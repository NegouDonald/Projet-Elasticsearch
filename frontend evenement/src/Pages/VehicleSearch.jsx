import { useState, useEffect } from "react";

const API_URL = "http://localhost:8080/api/vehicles";

const VehicleSearch = () => {
  const [vehicles, setVehicles] = useState([]);
  const [query, setQuery] = useState("");
  const [type, setType] = useState("");
  const [statut, setStatut] = useState("");
  const [anneeMin, setAnneeMin] = useState("");
  const [anneeMax, setAnneeMax] = useState("");
  const [loading, setLoading] = useState(false);

  // Fonction qui construit l'URL avec les filtres
  const buildUrl = () => {
    let url = `${API_URL}/search?q=${encodeURIComponent(query || "")}`;
    if (type) url += `&type=${encodeURIComponent(type)}`;
    if (statut) url += `&statut=${encodeURIComponent(statut)}`;
    if (anneeMin) url += `&anneeMin=${encodeURIComponent(anneeMin)}`;
    if (anneeMax) url += `&anneeMax=${encodeURIComponent(anneeMax)}`;
    return url;
  };

  // Fonction pour récupérer les véhicules filtrés
  const fetchVehicles = async () => {
    setLoading(true);
    const url = buildUrl();
    try {
      const res = await fetch(url);
      if (!res.ok) throw new Error("Erreur API");
      const data = await res.json();
      setVehicles(data);
    } catch (err) {
      console.error(err);
      setVehicles([]);
    }
    setLoading(false);
  };

  // On relance la recherche à chaque changement des filtres
  useEffect(() => {
    fetchVehicles();
  }, [query, type, statut, anneeMin, anneeMax]);

  return (
    <div className="p-6 max-w-4xl mx-auto">
      <h1 className="text-3xl font-bold mb-6">Recherche de véhicules</h1>

      <div className="mb-4 space-y-3">
        <input
          type="text"
          placeholder="Recherche par marque ou modèle"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          className="w-full border rounded-xl px-4 py-2"
        />

        <div className="flex flex-wrap gap-4">
          {/* Filtre Type */}
          <select
            value={type}
            onChange={(e) => setType(e.target.value)}
            className="border rounded-xl px-3 py-2"
          >
            <option value="">Tous les types</option>
            <option value="Voiture">Voiture</option>
            <option value="Camion">Camion</option>
            <option value="Moto">Moto</option>
          </select>

          {/* Filtre Statut */}
          <select
            value={statut}
            onChange={(e) => setStatut(e.target.value)}
            className="border rounded-xl px-3 py-2"
          >
            <option value="">Tous les statuts</option>
            <option value="Disponible">Disponible</option>
            <option value="Indisponible">Indisponible</option>
          </select>

          {/* Filtre Année Min */}
          <input
            type="number"
            placeholder="Année min"
            value={anneeMin}
            onChange={(e) => setAnneeMin(e.target.value)}
            className="border rounded-xl px-3 py-2 w-24"
            min="1900"
            max="2100"
          />

          {/* Filtre Année Max */}
          <input
            type="number"
            placeholder="Année max"
            value={anneeMax}
            onChange={(e) => setAnneeMax(e.target.value)}
            className="border rounded-xl px-3 py-2 w-24"
            min="1900"
            max="2100"
          />
        </div>
      </div>

      {loading ? (
        <p>Chargement...</p>
      ) : vehicles.length > 0 ? (
        <ul className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          {vehicles.map((v) => (
            <li key={v.id} className="border rounded-xl p-4 shadow hover:shadow-lg transition">
              <h2 className="text-xl font-semibold">{v.marque} {v.modele}</h2>
              <p>Année : {v.annee}</p>
              <p>Immatriculation : {v.immatriculation}</p>
              <p>Type : {v.type}</p>
              <p>Statut : {v.statut}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p>Aucun véhicule trouvé.</p>
      )}
    </div>
  );
};

export default VehicleSearch;
