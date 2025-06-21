import IndexPage from './IndexPage';

function VehiculesTransportPage() {
    return (
        <IndexPage
            serviceName="Service Transport - Véhicules"
            endpoint="vehicules_transport"
            fields={['marque', 'modele', 'annee', 'plaqueImmatriculation']}
        />
    );
}

export default VehiculesTransportPage;
