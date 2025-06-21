import IndexPage from './IndexPage';

function ElevesPage() {
    return (
        <IndexPage
            serviceName="Service Auto-école - Élèves"
            endpoint="eleves"
            fields={['nom', 'prenom', 'adresse', 'dateNaissance']}
        />
    );
}

export default ElevesPage;
