import ResultCard from './ResultCard';

function SearchResults({ results }) {
  if (!results || results.length === 0) {
    return (
      <div className="text-center py-16 animate-fade-in">
        <div className="w-24 h-24 mx-auto mb-6 bg-gradient-to-br from-gray-200 to-gray-300 rounded-full flex items-center justify-center shadow-lg">
          <svg className="w-12 h-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
          </svg>
        </div>
        <h3 className="text-2xl font-bold text-gray-600 mb-3">No results found</h3>
        <p className="text-gray-500 text-lg">Try adjusting your search terms or check your spelling</p>
      </div>
    );
  }

  const groupedResults = results.reduce((acc, result) => {
    const serviceName = result.serviceName;
    if (!acc[serviceName]) {
      acc[serviceName] = [];
    }
    acc[serviceName].push(result);
    return acc;
  }, {});

  return (
    <div className="space-y-12 mt-12 animate-fade-in">
      {Object.entries(groupedResults).map(([serviceName, items]) => (
        <section key={serviceName} className="animate-slide-up">
          {/* En-tête de section amélioré */}
          <div className="flex items-center mb-8">
            <div className="flex-1 h-px bg-gradient-to-r from-transparent via-gray-300 to-gray-300"></div>
            <div className="mx-6 px-6 py-3 bg-white rounded-full shadow-lg border border-gray-100">
              <h2 className="text-2xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
                {serviceName}
              </h2>
              <div className="text-sm text-gray-500 text-center mt-1">
                {items.length} result{items.length > 1 ? 's' : ''}
              </div>
            </div>
            <div className="flex-1 h-px bg-gradient-to-l from-transparent via-gray-300 to-gray-300"></div>
          </div>
          
          {/* Grille de résultats */}
          <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
            {items.map((result, index) => (
              <div 
                key={index} 
                className="animate-slide-up"
                style={{ animationDelay: `${index * 100}ms` }}
              >
                <ResultCard result={result} />
              </div>
            ))}
          </div>
        </section>
      ))}
    </div>
  );
}

export default SearchResults;
