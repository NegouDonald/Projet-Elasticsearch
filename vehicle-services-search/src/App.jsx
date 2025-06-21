import { useState, useEffect, useCallback, useRef } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SearchBar from './components/SearchBar';
import SearchResults from './components/SearchResults';
import ElevesPage from './pages/ElevesPage';
import InstructeursPage from './pages/InstructeursPage';
import LeconsPage from './pages/LeconsPage';
import VehiculesAutoEcolePage from './pages/VehiculesAutoEcolePage';
import VoituresLocationPage from './pages/VoituresLocationPage';
import ClientsLocationPage from './pages/ClientsLocationPage';
import ReservationsLocationPage from './pages/ReservationsLocationPage';
import ItinerairesPage from './pages/ItinerairesPage';
import VehiculesTransportPage from './pages/VehiculesTransportPage';
import ConducteursPage from './pages/ConducteursPage';
import HorairesPage from './pages/HorairesPage';

function App() {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [searchHistory, setSearchHistory] = useState([]);
  const [suggestions, setSuggestions] = useState([]);
  
  // Ref pour éviter les problèmes de closure
  const searchHistoryRef = useRef([]);

  // Charger l'historique depuis la mémoire au démarrage
  useEffect(() => {
    const savedHistory = window.searchHistoryData || [];
    setSearchHistory(savedHistory);
    searchHistoryRef.current = savedHistory;
  }, []);

  // Mettre à jour la ref quand l'historique change
  useEffect(() => {
    searchHistoryRef.current = searchHistory;
  }, [searchHistory]);

  // Sauvegarder l'historique en mémoire
  const saveSearchHistory = useCallback((history) => {
    window.searchHistoryData = history;
    setSearchHistory(history);
    searchHistoryRef.current = history;
  }, []);

  // Ajouter une recherche à l'historique
  const addToHistory = useCallback((query) => {
    if (query.trim() === '') return;
    
    setSearchHistory(prevHistory => {
      const newHistory = prevHistory.filter(item => item.query !== query);
      newHistory.unshift({
        query,
        timestamp: new Date().toISOString(),
        id: Date.now()
      });
      
      // Limiter l'historique à 50 éléments
      const limitedHistory = newHistory.slice(0, 50);
      window.searchHistoryData = limitedHistory;
      searchHistoryRef.current = limitedHistory;
      return limitedHistory;
    });
  }, []);

  // Effacer l'historique
  const clearHistory = useCallback(() => {
    saveSearchHistory([]);
  }, [saveSearchHistory]);

  // Supprimer un élément de l'historique
  const removeFromHistory = useCallback((id) => {
    setSearchHistory(prevHistory => {
      const updatedHistory = prevHistory.filter(item => item.id !== id);
      window.searchHistoryData = updatedHistory;
      searchHistoryRef.current = updatedHistory;
      return updatedHistory;
    });
  }, []);

  // Fonction pour obtenir des suggestions d'autocomplétion
  const getSuggestions = useCallback(async (query) => {
    console.log('getSuggestions called with:', query);
    
    if (!query || query.trim().length < 2) {
      setSuggestions([]);
      return;
    }

    try {
      // Utiliser la ref pour éviter les problèmes de closure
      const currentHistory = searchHistoryRef.current;
      
      // Suggestions basées sur l'historique
      const historySuggestions = currentHistory
        .filter(item => item.query.toLowerCase().includes(query.toLowerCase()))
        .slice(0, 3)
        .map(item => ({
          text: item.query,
          type: 'history',
          id: `history-${item.id}`
        }));

      // Suggestions prédéfinies pour les catégories
      const predefinedSuggestions = [
        'véhicules auto-école',
        'instructeurs',
        'élèves',
        'leçons de conduite',
        'voitures de location',
        'clients location',
        'réservations',
        'itinéraires transport',
        'véhicules transport',
        'conducteurs',
        'horaires'
      ].filter(item => item.toLowerCase().includes(query.toLowerCase()))
       .slice(0, 3)
       .map((item, index) => ({
         text: item,
         type: 'category',
         id: `category-${index}`
       }));

      // Suggestions depuis l'API
      let apiSuggestions = [];
      try {
        const response = await fetch(`/api/suggestions?q=${encodeURIComponent(query)}`);
        if (response.ok) {
          const data = await response.json();
          apiSuggestions = data.slice(0, 5).map((suggestion, index) => ({
            text: suggestion,
            type: 'api',
            id: `api-${index}`
          }));
        }
      } catch (apiError) {
        console.warn('API suggestions failed:', apiError);
      }

      // Combiner toutes les suggestions
      const allSuggestions = [
        ...historySuggestions,
        ...apiSuggestions,
        ...predefinedSuggestions
      ];

      // Supprimer les doublons et limiter à 8 suggestions
      const uniqueSuggestions = allSuggestions
        .filter((suggestion, index, self) => 
          index === self.findIndex(s => s.text.toLowerCase() === suggestion.text.toLowerCase())
        )
        .slice(0, 8);

      console.log('Setting suggestions:', uniqueSuggestions);
      setSuggestions(uniqueSuggestions);
    } catch (err) {
      console.error('Error fetching suggestions:', err);
      setSuggestions([]);
    }
  }, []); // Pas de dépendances car on utilise la ref

  const handleSearch = useCallback(async (query) => {
    console.log('handleSearch called with:', query);
    setLoading(true);
    setError(null);
    setSuggestions([]); // Masquer les suggestions pendant la recherche
    
    try {
      const response = await fetch(`/api/search?q=${encodeURIComponent(query)}`);
      if (!response.ok) {
        throw new Error('Failed to fetch search results');
      }
      const data = await response.json();
      setResults(data);
      addToHistory(query); // Ajouter à l'historique après une recherche réussie
    } catch (err) {
      setError(err.message);
      console.error('Search error:', err);
    } finally {
      setLoading(false);
    }
  }, [addToHistory]);

  // Composant pour afficher l'historique des recherches
  const SearchHistoryPanel = () => (
    <div className="bg-white rounded-xl shadow-lg border border-gray-200 p-6 mb-8">
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-lg font-semibold text-gray-800 flex items-center">
          <svg className="w-5 h-5 mr-2 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
          </svg>
          Historique des recherches
        </h3>
        {searchHistory.length > 0 && (
          <button
            onClick={clearHistory}
            className="text-sm text-red-600 hover:text-red-800 hover:bg-red-50 px-3 py-1 rounded-md transition-colors"
          >
            Effacer tout
          </button>
        )}
      </div>
      
      {searchHistory.length === 0 ? (
        <p className="text-gray-500 text-center py-4">Aucune recherche récente</p>
      ) : (
        <div className="space-y-2 max-h-60 overflow-y-auto">
          {searchHistory.slice(0, 10).map((item) => (
            <div
              key={item.id}
              className="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors group"
            >
              <button
                onClick={() => handleSearch(item.query)}
                className="flex-1 text-left flex items-center"
              >
                <svg className="w-4 h-4 mr-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                </svg>
                <span className="text-gray-700">{item.query}</span>
              </button>
              <div className="flex items-center space-x-2">
                <span className="text-xs text-gray-400">
                  {new Date(item.timestamp).toLocaleDateString()}
                </span>
                <button
                  onClick={() => removeFromHistory(item.id)}
                  className="text-gray-400 hover:text-red-600 opacity-0 group-hover:opacity-100 transition-opacity"
                >
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12"></path>
                  </svg>
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );

  // Composant pour la page d'accueil avec design amélioré
  const HomePage = () => (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50">
      {/* Header avec titre principal */}
      <header className="relative pt-16 pb-8">
        <div className="absolute inset-0">
          <div className="absolute inset-0 bg-gradient-to-r from-blue-600/20 via-purple-600/20 to-pink-600/20"></div>
          <div className="absolute inset-0 bg-[url('data:image/svg+xml,%3Csvg%20width=%2260%22%20height=%2260%22%20viewBox=%220%200%2060%2060%22%20xmlns=%22http://www.w3.org/2000/svg%22%3E%3Cg%20fill=%22none%22%20fill-rule=%22evenodd%22%3E%3Cg%20fill=%22%239C92AC%22%20fill-opacity=%220.05%22%3E%3Ccircle%20cx=%2230%22%20cy=%2230%22%20r=%224%22/%3E%3C/g%3E%3C/g%3E%3C/svg%3E')]"></div>
        </div>
        
        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <div className="animate-fade-in">
            <h1 className="text-5xl md:text-7xl font-black bg-gradient-to-r from-blue-600 via-purple-600 to-pink-600 bg-clip-text text-transparent mb-6 leading-tight">
              Vehicle Services
            </h1>
            <p className="text-xl md:text-2xl text-gray-600 font-medium max-w-3xl mx-auto leading-relaxed">
              Search for vehicles, instructors, students, and more with our intelligent search system
            </p>
          </div>
        </div>
      </header>

      {/* Zone de recherche */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="animate-slide-up">
          <SearchBar 
            onSearch={handleSearch} 
            onInputChange={getSuggestions}
            suggestions={suggestions}
            onSuggestionSelect={handleSearch}
          />
        </div>

        {/* Historique des recherches */}
        {!loading && !error && results.length === 0 && (
          <div className="animate-fade-in">
            <SearchHistoryPanel />
          </div>
        )}

        {/* États de chargement et d'erreur avec design amélioré */}
        {loading && (
          <div className="text-center py-16 animate-fade-in">
            <div className="w-16 h-16 mx-auto mb-6 border-4 border-blue-200 border-t-blue-600 rounded-full animate-spin"></div>
            <p className="text-xl text-gray-600 font-medium">Searching for results...</p>
          </div>
        )}

        {error && (
          <div className="text-center py-16 animate-fade-in">
            <div className="w-24 h-24 mx-auto mb-6 bg-gradient-to-br from-red-100 to-red-200 rounded-full flex items-center justify-center shadow-lg">
              <svg className="w-12 h-12 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <p className="text-xl text-red-600 font-semibold mb-2">Search Error</p>
            <p className="text-gray-600">{error}</p>
          </div>
        )}

        {/* Résultats de recherche */}
        <div className="pb-16">
          {results.length > 0 && <SearchResults results={results} />}
          {results.length === 0 && !loading && !error && searchHistory.length === 0 && (
            <div className="text-center py-16 animate-fade-in">
              <div className="w-24 h-24 mx-auto mb-6 bg-gradient-to-br from-gray-200 to-gray-300 rounded-full flex items-center justify-center shadow-lg">
                <svg className="w-12 h-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                </svg>
              </div>
              <h3 className="text-2xl font-bold text-gray-600 mb-3">Start Your Search</h3>
              <p className="text-gray-500 text-lg">Enter keywords to find vehicles, instructors, or students</p>
            </div>
          )}
        </div>
      </main>

      {/* Footer décoratif */}
      <footer className="relative mt-16 py-8 bg-gradient-to-r from-gray-50 to-gray-100">
        <div className="absolute inset-0 opacity-30">
          <div className="absolute inset-0 bg-[url('data:image/svg+xml,%3Csvg%20width=%22100%22%20height=%22100%22%20viewBox=%220%200%20100%20100%22%20xmlns=%22http://www.w3.org/2000/svg%22%3E%3Cpath%20d=%22M11%2018c3.866%200%207-3.134%207-7s-3.134-7-7-7-7%203.134-7%207%203.134%207%207%207zm48%2025c3.866%200%207-3.134%207-7s-3.134-7-7-7-7%203.134-7%207%203.134%207%207%207zm-43-7c1.657%200%203-1.343%203-3s-1.343-3-3-3-3%201.343-3%203%201.343%203%203%203zm63%2031c1.657%200%203-1.343%203-3s-1.343-3-3-3-3%201.343-3%203%201.343%203%203%203zM34%2090c1.657%200%203-1.343%203-3s-1.343-3-3-3-3%201.343-3%203%201.343%203%203%203zm56-76c1.657%200%203-1.343%203-3s-1.343-3-3-3-3%201.343-3%203%201.343%203%203%203zM12%2086c2.21%200%204-1.79%204-4s-1.79-4-4-4-4%201.79-4%204%201.79%204%204%204zm28-65c2.21%200%204-1.79%204-4s-1.79-4-4-4-4%201.79-4%204%201.79%204%204%204zm23-11c2.76%200%205-2.24%205-5s-2.24-5-5-5-5%202.24-5%205%202.24%205%205%205zm-6%2060c2.21%200%204-1.79%204-4s-1.79-4-4-4-4%201.79-4%204%201.79%204%204%204zm29%2022c2.76%200%205-2.24%205-5s-2.24-5-5-5-5%202.24-5%205%202.24%205%205%205zM32%2063c2.76%200%205-2.24%205-5s-2.24-5-5-5-5%202.24-5%205%202.24%205%205%205zm57-13c2.76%200%205-2.24%205-5s-2.24-5-5-5-5%202.24-5%205%202.24%205%205%205zm-9-21c1.105%200%202-.895%202-2s-.895-2-2-2-2%20.895-2%202%20.895%202%202%202zM60%2091c1.105%200%202-.895%202-2s-.895-2-2-2-2%20.895-2%202%20.895%202%202%202zM35%2041c1.105%200%202-.895%202-2s-.895-2-2-2-2%20.895-2%202%20.895%202%202%202zM12%2060c1.105%200%202-.895%202-2s-.895-2-2-2-2%20.895-2%202%20.895%202%202%202z%22%20fill=%22%23000%22%20fill-opacity=%220.02%22%20fill-rule=%22evenodd%22/%3E%3C/svg%3E')]"></div>
        </div>
        <div className="relative text-center">
          <p className="text-gray-500 text-sm">
            Connected to Spring Boot backend
          </p>
        </div>
      </footer>
    </div>
  );

  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/auto-ecole/eleves" element={<ElevesPage />} />
        <Route path="/auto-ecole/instructeurs" element={<InstructeursPage />} />
        <Route path="/auto-ecole/lecons" element={<LeconsPage />} />
        <Route path="/auto-ecole/vehicules" element={<VehiculesAutoEcolePage />} />
        <Route path="/location/voitures" element={<VoituresLocationPage />} />
        <Route path="/location/clients" element={<ClientsLocationPage />} />
        <Route path="/location/reservations" element={<ReservationsLocationPage />} />
        <Route path="/transport/itineraires" element={<ItinerairesPage />} />
        <Route path="/transport/vehicules" element={<VehiculesTransportPage />} />
        <Route path="/transport/conducteurs" element={<ConducteursPage />} />
        <Route path="/transport/horaires" element={<HorairesPage />} />
      </Routes>
    </Router>
  );
}

export default App;
