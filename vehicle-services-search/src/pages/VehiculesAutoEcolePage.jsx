import IndexPage from './IndexPage';

function VehiculesAutoEcolePage() {
    return (
        <IndexPage
            serviceName="Service Auto-école - Véhicules"
            endpoint="vehicules_auto_ecole"
            fields={['marque', 'modele', 'annee', 'plaqueImmatriculation']}
        />
    );
}

export default VehiculesAutoEcolePage;
