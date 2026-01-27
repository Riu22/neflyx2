let currentPage = 0;
const pageSize = 24;
const TMDB_API_KEY = 'f306b8cfe840576c37edcc364e803cbd';
const TMDB_IMAGE_BASE = 'https://image.tmdb.org/t/p/w500';

// Cache para imágenes de TMDb
const imageCache = new Map();

// Navbar scroll effect
window.addEventListener('scroll', () => {
    const navbar = document.getElementById('navbar');
    if (window.scrollY > 50) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

async function searchMovies(page = 0) {
    currentPage = page;
    showLoading(true);

    const params = buildSearchParams(page);

    try {
        const response = await fetch(`/movies/search?${params}`);

        if (!response.ok) {
            throw new Error('Error en la búsqueda');
        }

        const data = await response.json();
        showLoading(false);
        await renderMovies(data.content);
        renderPagination(data);

    } catch (error) {
        console.error('Error:', error);
        showLoading(false);
        showError('Error al cargar las películas. Por favor, intenta de nuevo.');
    }
}

function buildSearchParams(page) {
    const params = new URLSearchParams({
        page: page,
        size: pageSize
    });

    const keyword = document.getElementById('keyword').value.trim();
    const year = document.getElementById('year').value;
    const genre = document.getElementById('genre').value;
    const director = document.getElementById('director').value.trim();
    const actor = document.getElementById('actor').value.trim(); // ✨ AGREGADO

    if (keyword) params.append('keyword', keyword);
    if (year) params.append('year', year);
    if (genre) params.append('genre', genre);
    if (director) params.append('director', director);
    if (actor) params.append('actor', actor); // ✨ AGREGADO

    return params;
}

async function renderMovies(movies) {
    const grid = document.getElementById('movieGrid');
    const resultsSection = document.getElementById('resultsSection');
    const welcomeMessage = document.getElementById('welcomeMessage');

    if (!movies || movies.length === 0) {
        resultsSection.style.display = 'none';
        welcomeMessage.style.display = 'block';
        welcomeMessage.innerHTML = `
            <div class="no-results">
                <div class="no-results-icon">😔</div>
                <p>No se encontraron películas con esos filtros</p>
            </div>
        `;
        return;
    }

    welcomeMessage.style.display = 'none';
    resultsSection.style.display = 'block';

    const filters = [];
    const keyword = document.getElementById('keyword').value.trim();
    const year = document.getElementById('year').value;
    const genre = document.getElementById('genre').value;
    const director = document.getElementById('director').value.trim();
    const actor = document.getElementById('actor').value.trim(); // ✨ AGREGADO

    if (keyword) filters.push(`"${keyword}"`);
    if (year) filters.push(year);
    if (genre) filters.push(genre);
    if (director) filters.push(`director: ${director}`);
    if (actor) filters.push(`actor: ${actor}`); // ✨ AGREGADO

    const titleText = filters.length > 0
        ? `Resultados para: ${filters.join(' · ')}`
        : 'Todas las películas';

    document.getElementById('sectionTitle').textContent = titleText;

    // Renderizar cards con placeholders primero
    grid.innerHTML = movies.map(movie => createMovieCard(movie, null)).join('');

    // Cargar imágenes de TMDb en paralelo
    await loadMoviePosters(movies);
}

async function loadMoviePosters(movies) {
    const promises = movies.map(async (movie, index) => {
        const posterUrl = await getTMDbPoster(movie.title, movie.releaseDate);
        if (posterUrl) {
            // Actualizar solo el poster de esta película
            const cards = document.querySelectorAll('.movie-card');
            if (cards[index]) {
                const posterDiv = cards[index].querySelector('.movie-poster');
                posterDiv.style.backgroundImage = `url(${posterUrl})`;
                posterDiv.style.backgroundSize = 'cover';
                posterDiv.style.backgroundPosition = 'center';
                posterDiv.innerHTML = '';
            }
        }
    });

    await Promise.all(promises);
}

async function getTMDbPoster(title, releaseDate) {
    // Verificar cache
    const cacheKey = `${title}-${releaseDate}`;
    if (imageCache.has(cacheKey)) {
        return imageCache.get(cacheKey);
    }

    try {
        const year = releaseDate ? new Date(releaseDate).getFullYear() : '';
        const searchUrl = `https://api.themoviedb.org/3/search/movie?api_key=${TMDB_API_KEY}&query=${encodeURIComponent(title)}${year ? `&year=${year}` : ''}`;

        const response = await fetch(searchUrl);
        const data = await response.json();

        if (data.results && data.results.length > 0) {
            const posterPath = data.results[0].poster_path;
            if (posterPath) {
                const posterUrl = `${TMDB_IMAGE_BASE}${posterPath}`;
                imageCache.set(cacheKey, posterUrl);
                return posterUrl;
            }
        }
    } catch (error) {
        console.error('Error fetching TMDb poster:', error);
    }

    return null;
}

function createMovieCard(movie, posterUrl) {
    const year = movie.releaseDate ? new Date(movie.releaseDate).getFullYear() : 'N/A';
    const rating = movie.voteAverage ? parseFloat(movie.voteAverage).toFixed(1) : null;
    const genres = movie.genres || 'Sin géneros';
    const directors = movie.directors || 'Desconocido';

    // Mostrar match percentage si hay rating
    const matchPercentage = rating ? Math.round(parseFloat(rating) * 10) : null;

    const posterStyle = posterUrl
        ? `background-image: url(${posterUrl}); background-size: cover; background-position: center;`
        : '';

    return `
        <div class="movie-card">
            <a href="/movies/detail/${movie.movieId}" class="movie-link">
                <div class="movie-poster" style="${posterStyle}">
                    ${!posterUrl ? '🎬' : ''}
                </div>
                <div class="movie-info">
                    <div class="movie-title">${escapeHtml(movie.title)}</div>
                    ${matchPercentage ? `<div class="movie-rating">${matchPercentage}% Match</div>` : ''}
                    <div class="movie-meta">${year}</div>
                    <div class="movie-genres">${escapeHtml(genres)}</div>
                </div>
            </a>
        </div>
    `;
}

function renderPagination(data) {
    const pagination = document.getElementById('pagination');

    if (data.totalPages === 0) {
        pagination.style.display = 'none';
        return;
    }

    pagination.style.display = 'flex';

    const prevDisabled = data.first ? 'disabled' : '';
    const nextDisabled = data.last ? 'disabled' : '';

    pagination.innerHTML = `
        <button onclick="searchMovies(${currentPage - 1})" ${prevDisabled}>
            ← Anterior
        </button>

        <span class="pagination-info">
            Página <strong>${data.number + 1}</strong> de <strong>${data.totalPages}</strong>
        </span>

        <span class="page-count">(${data.totalElements} películas)</span>

        <button onclick="searchMovies(${currentPage + 1})" ${nextDisabled}>
            Siguiente →
        </button>
    `;
}

function showLoading(show) {
    document.getElementById('loading').style.display = show ? 'block' : 'none';
    if (show) {
        document.getElementById('resultsSection').style.display = 'none';
        document.getElementById('welcomeMessage').style.display = 'none';
        document.getElementById('pagination').style.display = 'none';
    }
}

function showError(message) {
    document.getElementById('welcomeMessage').style.display = 'block';
    document.getElementById('welcomeMessage').innerHTML = `
        <div class="no-results">
            <div class="no-results-icon">❌</div>
            <p>${escapeHtml(message)}</p>
        </div>
    `;
}

function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

document.addEventListener('DOMContentLoaded', function() {
    ['keyword', 'year', 'director', 'actor'].forEach(id => { // ✨ AGREGADO 'actor'
        document.getElementById(id).addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchMovies(0);
            }
        });
    });

    document.getElementById('genre').addEventListener('change', function() {
        if (this.value) {
            searchMovies(0);
        }
    });
});