export default function SearchBar({ onSearch }) {
    const [query, setQuery] = useState("");
  
    const handleSubmit = (e) => {
      e.preventDefault();
      onSearch(query);
    };
  
    return (
      <form onSubmit={handleSubmit} className="flex gap-2 p-4">
        <input
          type="text"
          className="border p-2 w-full"
          placeholder="Rechercher un document..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded">
          Chercher
        </button>
      </form>
    );
  }
  