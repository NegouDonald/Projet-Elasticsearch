const VehicleCard = ({ vehicle }) => {
  return (
    <div className="bg-white shadow-md p-4 rounded-xl border">
      <h2 className="text-xl font-bold">{vehicle.marque} {vehicle.modele}</h2>
      <p><strong>Année :</strong> {vehicle.annee}</p>
      <p><strong>Immatriculation :</strong> {vehicle.immatriculation}</p>
      <p><strong>Type :</strong> {vehicle.type}</p>
      <p><strong>Statut :</strong> {vehicle.statut}</p>
    </div>
  );
};

export default VehicleCard;
