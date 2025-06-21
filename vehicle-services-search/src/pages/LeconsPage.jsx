import IndexPage from './IndexPage';

function ElevesPage() {
    return (
        <IndexPage
    serviceName="Service Auto-école - Leçons"
    endpoint="lecons"
    fields={['dateLecon', 'heureLecon', 'dureeLecon', 'instructeurId', 'eleveId', 'vehiculeId']}
/>
    );
}

export default ElevesPage;
