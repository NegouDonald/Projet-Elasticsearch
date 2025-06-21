import { useState, useRef, useCallback } from 'react';

const SearchBar = ({ onSearch, onInputChange, suggestions = [], onSuggestionSelect }) => {
  const [query, setQuery] = useState('');
  const [showSuggestions, setShowSuggestions] = useState(false);
  const debounceRef = useRef(null);

  console.log('SearchBar render - query:', `"${query}"`);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submit with query:', `"${query}"`);
    if (query.trim() && onSearch) {
      onSearch(query.trim());
      setShowSuggestions(false);
    }
  };

  // Fonction de debounce avec cleanup
  const debouncedInputChange = useCallback((value) => {
    // Nettoyer le timeout précédent
    if (debounceRef.current) {
      clearTimeout(debounceRef.current);
    }

    // Créer un nouveau timeout
    debounceRef.current = setTimeout(() => {
      console.log('Calling onInputChange with debounced value:', `"${value}"`);
      if (onInputChange) {
        onInputChange(value);
      }
    }, 300);
  }, [onInputChange]);

  const handleInputChange = (e) => {
    const value = e.target.value;
    console.log('handleInputChange called with:', `"${value}"`);
    console.log('Previous query state:', `"${query}"`);
    
    // Mettre à jour l'état
    setQuery(value);
    console.log('setQuery called with:', `"${value}"`);
    
    // Gérer l'affichage des suggestions
    setShowSuggestions(value.length > 0);
    
    // Appeler la fonction debounced pour les suggestions
    if (value.length >= 2) {
      debouncedInputChange(value);
    } else if (value.length < 2) {
      // Nettoyer le timeout si la requête est trop courte
      if (debounceRef.current) {
        clearTimeout(debounceRef.current);
      }
      // Vider les suggestions immédiatement
      if (onInputChange) {
        onInputChange('');
      }
    }
  };

  const handleSuggestionClick = (suggestion) => {
    const text = suggestion?.text || suggestion;
    console.log('Suggestion clicked:', text);
    setQuery(text);
    setShowSuggestions(false);
    if (onSuggestionSelect) {
      onSuggestionSelect(text);
    }
  };

  // Cleanup du timeout au démontage du composant
  const handleBlur = () => {
    setTimeout(() => setShowSuggestions(false), 200);
  };

  return (
    <div className="max-w-4xl mx-auto mb-8">
      <form onSubmit={handleSubmit}>
        <div className="bg-white rounded-lg shadow-md border p-4">
          <div className="flex items-center gap-4">
            <input
              type="text"
              value={query}
              onChange={handleInputChange}
              onFocus={() => query.length > 0 && setShowSuggestions(true)}
              onBlur={handleBlur}
              placeholder="Tapez votre recherche..."
              className="flex-1 p-2 border rounded focus:outline-none focus:border-blue-500"
              autoComplete="off"
            />
            <button
              type="submit"
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
            >
              Rechercher
            </button>
          </div>
        </div>
      </form>

      {/* Debug info détaillé */}
      <div className="mt-4 p-3 bg-gray-100 rounded text-sm space-y-1">
        <div><strong>Query state:</strong> "{query}" (length: {query.length})</div>
        <div><strong>Suggestions length:</strong> {suggestions?.length || 0}</div>
        <div><strong>Show suggestions:</strong> {showSuggestions.toString()}</div>
        <div><strong>Render time:</strong> {new Date().toLocaleTimeString()}</div>
        <div><strong>Query type:</strong> {typeof query}</div>
      </div>

      {/* Suggestions */}
      {showSuggestions && suggestions?.length > 0 && (
        <div className="mt-2 bg-white border rounded shadow-lg z-50">
          {suggestions.slice(0, 5).map((suggestion, index) => (
            <button
              key={suggestion?.id || index}
              onClick={() => handleSuggestionClick(suggestion)}
              className="block w-full text-left p-3 hover:bg-gray-100 border-b last:border-b-0"
            >
              {suggestion?.text || suggestion}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default SearchBar;
