import { useState } from "react";

const initialVehicle = {
  id: "",
  marque: "",
  modele: "",
  annee: "",
  immatriculation: "",
  type: "",
  statut: "",
};

const VehicleForm = ({ onVehicleAdded }) => {
  const [vehicle, setVehicle] = useState(initialVehicle);
  const [showForm, setShowForm] = useState(false);

  const handleChange = (e) => {
    setVehicle({ ...vehicle, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch("http://localhost:8080/api/vehicles", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(vehicle),
    });

    if (response.ok) {
      onVehicleAdded();
      setVehicle(initialVehicle);
      setShowForm(false);
    } else {
      alert("Erreur lors de l'ajout du véhicule.");
    }
  };

  return (
    <div className="mb-6">
      <button
        onClick={() => setShowForm(!showForm)}
        className="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700 mb-4"
      >
        {showForm ? "Annuler" : "Ajouter un véhicule"}
      </button>

      {showForm && (
        <form onSubmit={handleSubmit} className="grid gap-4 bg-white p-4 rounded-xl shadow-md">
          <input type="text" name="id" placeholder="ID" required value={vehicle.id} onChange={handleChange} className="border rounded px-3 py-2" />
          <input type="text" name="marque" placeholder="Marque" required value={vehicle.marque} onChange={handleChange} className="border rounded px-3 py-2" />
          <input type="text" name="modele" placeholder="Modèle" required value={vehicle.modele} onChange={handleChange} className="border rounded px-3 py-2" />
          <input type="number" name="annee" placeholder="Année" required value={vehicle.annee} onChange={handleChange} className="border rounded px-3 py-2" />
          <input type="text" name="immatriculation" placeholder="Immatriculation" required value={vehicle.immatriculation} onChange={handleChange} className="border rounded px-3 py-2" />
          <input type="text" name="type" placeholder="Type" required value={vehicle.type} onChange={handleChange} className="border rounded px-3 py-2" />
          <input type="text" name="statut" placeholder="Statut" required value={vehicle.statut} onChange={handleChange} className="border rounded px-3 py-2" />

          <button type="submit" className="bg-green-600 text-white px-4 py-2 rounded-xl hover:bg-green-700">
            Enregistrer
          </button>
        </form>
      )}
    </div>
  );
};

export default VehicleForm;
