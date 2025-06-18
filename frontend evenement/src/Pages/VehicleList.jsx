import { useEffect, useState } from "react";
import VehicleCard from "../components/VehicleCard";
import VehicleForm from "../components/VehicleForm";

const API_URL = "http://localhost:8080/api/vehicles";

const VehicleList = () => {
  const [vehicles, setVehicles] = useState([]);
  const [query, setQuery] = useState("");

  const fetchVehicles = async () => {
    const url = query ? `${API_URL}/search?q=${query}` : API_URL;
    const res = await fetch(url);
    const data = await res.json();
    setVehicles(data);
  };

  useEffect(() => {
    fetchVehicles();
  }, [query]);

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-4">Liste des véhicules</h1>

      <VehicleForm onVehicleAdded={fetchVehicles} />

      <input
        type="text"
        placeholder="Rechercher par marque ou modèle..."
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        className="border rounded-xl px-4 py-2 w-full mb-6"
      />

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        {vehicles.length > 0 ? (
          vehicles.map((v) => <VehicleCard key={v.id} vehicle={v} />)
        ) : (
          <p>Aucun véhicule trouvé.</p>
        )}
      </div>
    </div>
  );
};

export default VehicleList;
