import IndexPage from './IndexPage';

function ItinerairesPage() {
    return (
        <IndexPage
            serviceName="Service Transport - Itinéraires"
            endpoint="itineraires"
            fields={['depart', 'arrivee']}
        />
    );
}

export default ItinerairesPage;
