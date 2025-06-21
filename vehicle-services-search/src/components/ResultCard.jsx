function ResultCard({ result }) {
  const getDisplayInfo = (entity) => {
    if (entity.nom && entity.prenom) {
      return {
        text: `${entity.nom} ${entity.prenom}`,
        icon: '👤',
        type: 'Person',
        color: 'from-green-500 to-emerald-500'
      };
    } else if (entity.marque && entity.modele) {
      return {
        text: `${entity.marque} ${entity.modele}`,
        icon: '🚗',
        type: 'Vehicle',
        color: 'from-blue-500 to-cyan-500'
      };
    } else if (entity.depart && entity.arrivee) {
      return {
        text: `${entity.depart} → ${entity.arrivee}`,
        icon: '🗺️',
        type: 'Route',
        color: 'from-purple-500 to-pink-500'
      };
    } else if (entity.dateLecon) {
      return {
        text: `Lesson on ${new Date(entity.dateLecon).toLocaleDateString()}`,
        icon: '📅',
        type: 'Lesson',
        color: 'from-orange-500 to-red-500'
      };
    } else if (entity.dateDebut && entity.dateFin) {
      return {
        text: `${new Date(entity.dateDebut).toLocaleDateString()} - ${new Date(entity.dateFin).toLocaleDateString()}`,
        icon: '⏰',
        type: 'Rental',
        color: 'from-indigo-500 to-purple-500'
      };
    } else if (entity.date && entity.heureDepart) {
      return {
        text: `Horaire le ${new Date(entity.date).toLocaleDateString()} à ${entity.heureDepart}`,
        icon: '🕒',
        type: 'Schedule',
        color: 'from-teal-500 to-cyan-500'
      };
    }
    return {
      text: JSON.stringify(entity),
      icon: '❓',
      type: 'Unknown',
      color: 'from-gray-500 to-gray-600'
    };
  };

  const { text, icon, type, color } = getDisplayInfo(result.entity);

  return (
    <a
      href={result.webUrl}
      target="_blank"
      rel="noopener noreferrer"
      className="group block relative overflow-hidden rounded-2xl bg-white shadow-lg hover:shadow-2xl transition-all duration-500 transform hover:-translate-y-2 hover:scale-105 border border-gray-100"
    >
      {/* Gradient de fond */}
      <div className={`absolute inset-0 bg-gradient-to-br ${color} opacity-0 group-hover:opacity-5 transition-opacity duration-300`}></div>
      
      <div className="relative p-6">
        {/* Header avec icône et type */}
        <div className="flex items-center justify-between mb-4">
          <div className="flex items-center space-x-3">
            <div className={`w-12 h-12 bg-gradient-to-br ${color} rounded-xl flex items-center justify-center text-white text-xl shadow-lg transform group-hover:scale-110 transition-transform duration-300`}>
              {icon}
            </div>
            <div>
              <span className="inline-block px-3 py-1 text-xs font-semibold text-white bg-gradient-to-r from-gray-600 to-gray-700 rounded-full mb-1">
                {type}
              </span>
            </div>
          </div>
          
          {/* Icône externe */}
          <svg className="w-5 h-5 text-gray-400 group-hover:text-blue-500 transition-colors duration-300 transform group-hover:scale-110" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14"></path>
          </svg>
        </div>
        
        {/* Contenu principal */}
        <h3 className="text-lg font-bold text-gray-800 mb-3 group-hover:text-blue-600 transition-colors duration-300 leading-tight">
          {text}
        </h3>
        
        {/* Footer */}
        <div className="flex items-center justify-between">
          <span className={`text-sm font-semibold text-white px-3 py-1 rounded-full bg-gradient-to-r ${color} shadow-sm`}>
            {result.serviceName}
          </span>
          <div className="w-2 h-2 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full opacity-0 group-hover:opacity-100 transition-opacity duration-300 animate-bounce-subtle"></div>
        </div>
      </div>
      
      {/* Effet de bordure au survol */}
      <div className="absolute inset-0 rounded-2xl border-2 border-transparent group-hover:border-blue-200 transition-colors duration-300"></div>
    </a>
  );
}

export default ResultCard;
