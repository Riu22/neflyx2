// --- CONFIGURACIÓN GLOBAL ---
let currentPage = 0;
const pageSize = 24;
const TMDB_API_KEY = 'f306b8cfe840576c37edcc364e803cbd';
const TMDB_IMAGE_BASE = 'https://image.tmdb.org/t/p/w500';
const imageCache = new Map();

// --- 1. INICIALIZACIÓN DE AUTOCOMPLETE.JS (Librería) ---
document.addEventListener('DOMContentLoaded', () => {

    const autoCompleteJS = new autoComplete({
        selector: "#keyword",
        placeHolder: "Busca una película...",
        data: {
            src: async (query) => {
                try {
                    // Llamada a tu controlador de Spring Boot
                    const source = await fetch(`/movies/autocomplete?term=${encodeURIComponent(query)}`);
                    const data = await source.json();
                    return data;
                } catch (error) {
                    console.error("Error cargando sugerencias:", error);
                    return [];
                }
            },
            cache: false,
        },
        resultsList: {
            element: (list, data) => {
                if (!data.results.length) {
                    const message = document.createElement("div");
                    message.setAttribute("class", "no_result");
                    message.innerHTML = `<span>No hay resultados para "${data.query}"</span>`;
                    list.prepend(message);
                }
            },
            noResults: true,
            maxResults: 8,
            tabSelect: true
        },
        resultItem: {
            highlight: true
        },
        events: {
            input: {
                selection: (event) => {
                    const selection = event.detail.selection.value;
                    autoCompleteJS.input.value = selection;
                    // Al seleccionar, lanza la búsqueda automáticamente
                    searchMovies(0);
                }
            }
        }
    });

    // Configurar eventos para el resto de filtros manuales
    ['year', 'director', 'actor', 'character'].forEach(id => {
        const el = document.getElementById(id);
        if (el) {
            el.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') searchMovies(0);
            });
        }
    });

    const genreSelect = document.getElementById('genre');
    if (genreSelect) {
        genreSelect.addEventListener('change', () => searchMovies(0));
    }
});

// --- 2. LÓGICA DE BÚSQUEDA Y PAGINACIÓN ---
async function searchMovies(page = 0) {
    currentPage = page;
    showLoading(true);
    const params = buildSearchParams(page);

    try {
        const response = await fetch(`/movies/search?${params}`);
        if (!response.ok) throw new Error('Error en la búsqueda');

        const data = await response.json();
        showLoading(false);

        await renderMovies(data.content);
        renderPagination(data);
    } catch (error) {
        showLoading(false);
        showError('Error al conectar con el servidor.');
    }
}

function buildSearchParams(page) {
    const params = new URLSearchParams({ page: page, size: pageSize });
    const fields = ['keyword', 'year', 'genre', 'director', 'actor', 'character'];

    fields.forEach(id => {
        const val = document.getElementById(id).value.trim();
        if (val) params.append(id, val);
    });
    return params;
}

// --- 3. RENDERIZADO Y POSTERS ---
async function renderMovies(movies) {
    const grid = document.getElementById('movieGrid');
    const resultsSection = document.getElementById('resultsSection');
    const welcomeMessage = document.getElementById('welcomeMessage');

    if (!movies || movies.length === 0) {
        resultsSection.style.display = 'none';
        welcomeMessage.style.display = 'block';
        welcomeMessage.innerHTML = `<div class="no-results"><p>No se encontraron resultados</p></div>`;
        return;
    }

    welcomeMessage.style.display = 'none';
    resultsSection.style.display = 'block';

    grid.innerHTML = movies.map(movie => createMovieCard(movie)).join('');

    // Carga asíncrona de imágenes de TMDb
    loadMoviePosters(movies);
}

function createMovieCard(movie) {
    const year = movie.releaseDate ? new Date(movie.releaseDate).getFullYear() : 'N/A';
    const rating = movie.voteAverage ? Math.round(movie.voteAverage * 10) : 0;

    return `
        <div class="movie-card">
            <a href="/movies/detail/${movie.movieId}" class="movie-link">
                <div class="movie-poster">🎬</div>
                <div class="movie-info">
                    <div class="movie-title">${escapeHtml(movie.title)}</div>
                    <div class="movie-rating">${rating}% Match</div>
                    <div class="movie-meta">${year}</div>
                </div>
            </a>
        </div>`;
}

async function loadMoviePosters(movies) {
    const promises = movies.map(async (movie, index) => {
        const posterUrl = await getTMDbPoster(movie.title, movie.releaseDate);
        if (posterUrl) {
            const cards = document.querySelectorAll('.movie-card');
            if (cards[index]) {
                const posterDiv = cards[index].querySelector('.movie-poster');
                posterDiv.style.backgroundImage = `url(${posterUrl})`;
                posterDiv.innerHTML = '';
            }
        }
    });
    await Promise.all(promises);
}

async function getTMDbPoster(title, releaseDate) {
    const cacheKey = `${title}-${releaseDate}`;
    if (imageCache.has(cacheKey)) return imageCache.get(cacheKey);

    try {
        const year = releaseDate ? new Date(releaseDate).getFullYear() : '';
        const url = `https://api.themoviedb.org/3/search/movie?api_key=${TMDB_API_KEY}&query=${encodeURIComponent(title)}${year ? `&year=${year}` : ''}`;
        const response = await fetch(url);
        const data = await response.json();

        if (data.results?.length > 0 && data.results[0].poster_path) {
            const fullUrl = `${TMDB_IMAGE_BASE}${data.results[0].poster_path}`;
            imageCache.set(cacheKey, fullUrl);
            return fullUrl;
        }
    } catch (e) { console.error("TMDb Error:", e); }
    return null;
}

// --- 4. UTILIDADES ---
function renderPagination(data) {
    const pagination = document.getElementById('pagination');
    if (data.totalPages <= 1) {
        pagination.style.display = 'none';
        return;
    }
    pagination.style.display = 'flex';
    pagination.innerHTML = `
        <button onclick="searchMovies(${currentPage - 1})" ${data.first ? 'disabled' : ''}>Anterior</button>
        <span class="pagination-info">Página ${data.number + 1} de ${data.totalPages}</span>
        <button onclick="searchMovies(${currentPage + 1})" ${data.last ? 'disabled' : ''}>Siguiente</button>
    `;
}

function showLoading(show) {
    document.getElementById('loading').style.display = show ? 'block' : 'none';
    if (show) {
        document.getElementById('resultsSection').style.display = 'none';
        document.getElementById('welcomeMessage').style.display = 'none';
    }
}

function showError(msg) {
    const welcome = document.getElementById('welcomeMessage');
    welcome.style.display = 'block';
    welcome.innerHTML = `<div class="no-results"><p>❌ ${escapeHtml(msg)}</p></div>`;
}

function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}