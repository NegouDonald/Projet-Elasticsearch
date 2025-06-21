import IndexPage from './IndexPage';

function VoituresLocationPage() {
    return (
        <IndexPage
            serviceName="Service Location de Voitures"
            endpoint="voitures_location"
            fields={['marque', 'modele', 'annee', 'plaqueImmatriculation']}
        />
    );
}

export default VoituresLocationPage;
