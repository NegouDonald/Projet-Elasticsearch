import React, { useState, useEffect } from 'react';
import { Ship, Container, Clock, AlertTriangle, CheckCircle, BarChart, MapPin, Users, Calendar, Settings } from 'lucide-react';

const ContainerTerminalExpertSystem = () => {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [ships, setShips] = useState([
    {
      id: 1,
      name: "MSC Maersk",
      capacity: 3000,
      arrival: "2025-05-28T14:00",
      status: "En route",
      quai: null,
      containers: 2850,
      priority: "haute"
    },
    {
      id: 2,
      name: "CMA CGM Liberty",
      capacity: 2500,
      arrival: "2025-05-29T08:00",
      status: "Planifié",
      quai: "A2",
      containers: 2200,
      priority: "moyenne"
    }
  ]);

  const [quais, setQuais] = useState([
    { id: "A1", longueur: 350, disponible: true, navire: null, profondeur: 16 },
    { id: "A2", longueur: 300, disponible: false, navire: "CMA CGM Liberty", profondeur: 14 },
    { id: "B1", longueur: 280, disponible: true, navire: null, profondeur: 12 },
    { id: "B2", longueur: 400, disponible: true, navire: null, profondeur: 18 }
  ]);

  const [containers, setContainers] = useState([
    { id: "MSKU123456", type: "20'", statut: "Déchargé", destination: "Douala", douane: "Vérifié" },
    { id: "CGMU789012", type: "40'", statut: "En attente", destination: "Yaoundé", douane: "En cours" },
    { id: "MAEU345678", type: "40'HC", statut: "Chargé", destination: "Export", douane: "Validé" }
  ]);

  const [recommendations, setRecommendations] = useState([]);

  const assignQuai = (shipId, quaiId) => {
    const ship = ships.find(s => s.id === shipId);
    if (!ship) return;
    
    setShips(prev => prev.map(s => 
      s.id === shipId ? { ...s, quai: quaiId, status: "Assigné" } : s
    ));
    setQuais(prev => prev.map(q => 
      q.id === quaiId ? { ...q, disponible: false, navire: ship.name } : q
    ));
    
    alert(`Quai ${quaiId} assigné au navire ${ship.name} avec succès!`);
  };

  const addNewShip = () => {
    const newShip = {
      id: ships.length + 1,
      name: `Navire ${ships.length + 1}`,
      capacity: 2000 + Math.floor(Math.random() * 2000),
      arrival: new Date(Date.now() + Math.random() * 7 * 24 * 60 * 60 * 1000).toISOString(),
      status: "Planifié",
      quai: null,
      containers: Math.floor(Math.random() * 3000),
      priority: Math.random() > 0.5 ? "haute" : "moyenne"
    };
    setShips(prev => [...prev, newShip]);
    alert(`Nouveau navire ${newShip.name} ajouté avec succès!`);
  };

  const searchContainer = (searchTerm) => {
    if (!searchTerm.trim()) {
      alert("Veuillez entrer un terme de recherche");
      return;
    }
    
    const found = containers.find(c => 
      c.id.toLowerCase().includes(searchTerm.toLowerCase()) ||
      c.destination.toLowerCase().includes(searchTerm.toLowerCase())
    );
    
    if (found) {
      alert(`Conteneur trouvé: ${found.id} - Statut: ${found.statut} - Destination: ${found.destination}`);
    } else {
      alert("Aucun conteneur trouvé avec ce critère");
    }
  };

  const releaseQuai = (quaiId) => {
    setQuais(prev => prev.map(q => 
      q.id === quaiId ? { ...q, disponible: true, navire: null } : q
    ));
    setShips(prev => prev.map(s => 
      s.quai === quaiId ? { ...s, quai: null, status: "En route" } : s
    ));
    alert(`Quai ${quaiId} libéré avec succès!`);
  };

  useEffect(() => {
    const generateRecommendations = () => {
      const newRecommendations = [];

      ships.forEach(ship => {
        if (ship.status === "En route" && !ship.quai) {
          const suitableQuais = quais.filter(q => 
            q.disponible && 
            q.longueur >= (ship.capacity > 2500 ? 350 : 300) &&
            q.profondeur >= (ship.capacity > 2500 ? 16 : 14)
          );
          
          if (suitableQuais.length > 0) {
            newRecommendations.push({
              type: "attribution",
              priority: ship.priority === "haute" ? "high" : "medium",
              message: `Attribuer le quai ${suitableQuais[0].id} au navire ${ship.name}`,
              action: () => assignQuai(ship.id, suitableQuais[0].id)
            });
          } else {
            newRecommendations.push({
              type: "alert",
              priority: "high",
              message: `Aucun quai disponible pour ${ship.name} - Optimiser la planification`,
              action: null
            });
          }
        }
      });

      const containersEnAttente = containers.filter(c => c.douane === "En cours");
      if (containersEnAttente.length > 5) {
        newRecommendations.push({
          type: "douane",
          priority: "medium",
          message: `${containersEnAttente.length} conteneurs en attente douanière - Contacter les services`,
          action: null
        });
      }

      const shipsHautePriorite = ships.filter(s => s.priority === "haute" && s.status === "En route");
      if (shipsHautePriorite.length > 0) {
        newRecommendations.push({
          type: "priority",
          priority: "high",
          message: `${shipsHautePriorite.length} navire(s) haute priorité nécessitent une attention immédiate`,
          action: null
        });
      }

      setRecommendations(newRecommendations);
    };

    generateRecommendations();
  }, [ships, quais, containers]);

  const Dashboard = () => (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="bg-blue-50 p-4 rounded-lg border border-blue-200">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-blue-600 text-sm font-medium">Navires en cours</p>
              <p className="text-2xl font-bold text-blue-800">{ships.length}</p>
            </div>
            <Ship className="text-blue-600" size={24} />
          </div>
        </div>
        
        <div className="bg-green-50 p-4 rounded-lg border border-green-200">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-green-600 text-sm font-medium">Quais disponibles</p>
              <p className="text-2xl font-bold text-green-800">{quais.filter(q => q.disponible).length}</p>
            </div>
            <MapPin className="text-green-600" size={24} />
          </div>
        </div>
        
        <div className="bg-orange-50 p-4 rounded-lg border border-orange-200">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-orange-600 text-sm font-medium">Conteneurs actifs</p>
              <p className="text-2xl font-bold text-orange-800">{containers.length}</p>
            </div>
            <Container className="text-orange-600" size={24} />
          </div>
        </div>
        
        <div className="bg-red-50 p-4 rounded-lg border border-red-200">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-red-600 text-sm font-medium">Alertes actives</p>
              <p className="text-2xl font-bold text-red-800">{recommendations.filter(r => r.priority === "high").length}</p>
            </div>
            <AlertTriangle className="text-red-600" size={24} />
          </div>
        </div>
      </div>

      <div className="bg-white p-6 rounded-lg shadow-md">
        <h3 className="text-lg font-semibold mb-4 flex items-center">
          <Settings className="mr-2" size={20} />
          Recommandations du Système Expert
        </h3>
        <div className="space-y-3">
          {recommendations.length > 0 ? recommendations.map((rec, index) => (
            <div key={index} className={`p-4 rounded-lg border-l-4 ${
              rec.priority === 'high' ? 'bg-red-50 border-red-400' : 
              rec.priority === 'medium' ? 'bg-yellow-50 border-yellow-400' : 
              'bg-blue-50 border-blue-400'
            }`}>
              <div className="flex items-center justify-between">
                <p className="text-sm">{rec.message}</p>
                {rec.action && (
                  <button 
                    onClick={() => {
                      rec.action();
                      setTimeout(() => {
                        setRecommendations(prev => prev.filter((_, i) => i !== index));
                      }, 500);
                    }}
                    className="px-3 py-1 bg-blue-600 text-white text-xs rounded hover:bg-blue-700 transition-colors"
                  >
                    Appliquer
                  </button>
                )}
              </div>
            </div>
          )) : (
            <p className="text-gray-500 text-sm">Aucune recommandation pour le moment</p>
          )}
        </div>
      </div>
    </div>
  );

  const ShipManagement = () => (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-xl font-semibold">Gestion des Navires</h2>
        <button 
          onClick={addNewShip}
          className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors"
        >
          Nouveau Navire
        </button>
      </div>
      
      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        <table className="w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Navire</th>
              <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Capacité</th>
              <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Arrivée</th>
              <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Statut</th>
              <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Quai</th>
              <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Priorité</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-200">
            {ships.map(ship => (
              <tr key={ship.id} className="hover:bg-gray-50">
                <td className="px-6 py-4 whitespace-nowrap">
                  <div className="flex items-center">
                    <Ship className="mr-2 text-blue-600" size={16} />
                    {ship.name}
                  </div>
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {ship.capacity} EVP
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {new Date(ship.arrival).toLocaleString('fr-FR')}
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className={`px-2 py-1 text-xs rounded-full ${
                    ship.status === 'En route' ? 'bg-yellow-100 text-yellow-800' :
                    ship.status === 'Assigné' ? 'bg-green-100 text-green-800' :
                    'bg-blue-100 text-blue-800'
                  }`}>
                    {ship.status}
                  </span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {ship.quai || 'Non assigné'}
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className={`px-2 py-1 text-xs rounded-full ${
                    ship.priority === 'haute' ? 'bg-red-100 text-red-800' :
                    'bg-gray-100 text-gray-800'
                  }`}>
                    {ship.priority}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );

  const QuaiManagement = () => (
    <div className="space-y-6">
      <h2 className="text-xl font-semibold">Gestion des Quais</h2>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {quais.map(quai => (
          <div key={quai.id} className={`p-6 rounded-lg shadow-md ${
            quai.disponible ? 'bg-green-50 border border-green-200' : 'bg-red-50 border border-red-200'
          }`}>
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold">Quai {quai.id}</h3>
              <div className={`px-3 py-1 rounded-full text-sm ${
                quai.disponible ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
              }`}>
                {quai.disponible ? 'Disponible' : 'Occupé'}
              </div>
            </div>
            
            <div className="space-y-2 text-sm">
              <div className="flex justify-between">
                <span className="text-gray-600">Longueur:</span>
                <span className="font-medium">{quai.longueur}m</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-600">Profondeur:</span>
                <span className="font-medium">{quai.profondeur}m</span>
              </div>
              {quai.navire && (
                <div className="flex justify-between">
                  <span className="text-gray-600">Navire:</span>
                  <span className="font-medium">{quai.navire}</span>
                </div>
              )}
              {quai.navire && (
                <div className="mt-4">
                  <button 
                    onClick={() => releaseQuai(quai.id)}
                    className="w-full px-3 py-2 bg-red-600 text-white text-sm rounded hover:bg-red-700 transition-colors"
                  >
                    Libérer le quai
                  </button>
                </div>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );

  const ContainerTracking = () => {
    const [searchTerm, setSearchTerm] = useState('');
    
    return (
      <div className="space-y-6">
        <div className="flex justify-between items-center">
          <h2 className="text-xl font-semibold">Suivi des Conteneurs</h2>
          <div className="flex space-x-2">
            <input 
              type="text" 
              placeholder="Rechercher un conteneur..." 
              className="px-3 py-2 border border-gray-300 rounded"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button 
              onClick={() => searchContainer(searchTerm)}
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors"
            >
              Rechercher
            </button>
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow-md overflow-hidden">
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">ID Conteneur</th>
                <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Type</th>
                <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Statut</th>
                <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Destination</th>
                <th className="px-6 py-3 text-left text-sm font-medium text-gray-500 uppercase">Douane</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {containers.map(container => (
                <tr key={container.id} className="hover:bg-gray-50">
                  <td className="px-6 py-4 whitespace-nowrap font-mono text-sm">
                    {container.id}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {container.type}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`px-2 py-1 text-xs rounded-full ${
                      container.statut === 'Déchargé' ? 'bg-green-100 text-green-800' :
                      container.statut === 'En attente' ? 'bg-yellow-100 text-yellow-800' :
                      'bg-blue-100 text-blue-800'
                    }`}>
                      {container.statut}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {container.destination}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`px-2 py-1 text-xs rounded-full ${
                      container.douane === 'Vérifié' ? 'bg-green-100 text-green-800' :
                      container.douane === 'En cours' ? 'bg-yellow-100 text-yellow-800' :
                      'bg-blue-100 text-blue-800'
                    }`}>
                      {container.douane}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  };

  const tabs = [
    { id: 'dashboard', label: 'Tableau de Bord', icon: BarChart },
    { id: 'ships', label: 'Navires', icon: Ship },
    { id: 'quais', label: 'Quais', icon: MapPin },
    { id: 'containers', label: 'Conteneurs', icon: Container }
  ];

  return (
    <div className="min-h-screen bg-gray-100">
      <header className="bg-blue-800 text-white p-4">
        <div className="max-w-7xl mx-auto">
          <h1 className="text-2xl font-bold">Système Expert - Terminal à Conteneurs Kribi</h1>
          <p className="text-blue-100 mt-1">Gestion intelligente et optimisation logistique</p>
        </div>
      </header>

      <nav className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4">
          <div className="flex space-x-8">
            {tabs.map(tab => {
              const Icon = tab.icon;
              return (
                <button
                  key={tab.id}
                  onClick={() => setActiveTab(tab.id)}
                  className={`flex items-center px-3 py-4 text-sm font-medium border-b-2 ${
                    activeTab === tab.id 
                      ? 'border-blue-500 text-blue-600' 
                      : 'border-transparent text-gray-500 hover:text-gray-700'
                  }`}
                >
                  <Icon className="mr-2" size={16} />
                  {tab.label}
                </button>
              );
            })}
          </div>
        </div>
      </nav>

      <main className="max-w-7xl mx-auto px-4 py-8">
        {activeTab === 'dashboard' && <Dashboard />}
        {activeTab === 'ships' && <ShipManagement />}
        {activeTab === 'quais' && <QuaiManagement />}
        {activeTab === 'containers' && <ContainerTracking />}
      </main>

      <div className="fixed bottom-0 left-0 right-0 bg-gray-800 text-white p-2">
        <div className="max-w-7xl mx-auto flex justify-between items-center text-sm">
          <div className="flex items-center space-x-4">
            <span className="flex items-center">
              <CheckCircle className="mr-1 text-green-400" size={16} />
              Système opérationnel
            </span>
            <span>Port Autonome de Kribi - Terminal Conteneurs</span>
          </div>
          <div className="flex items-center">
            <Clock className="mr-1" size={16} />
            {new Date().toLocaleString('fr-FR')}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ContainerTerminalExpertSystem;