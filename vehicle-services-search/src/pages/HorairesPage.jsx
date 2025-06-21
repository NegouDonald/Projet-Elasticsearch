import IndexPage from './IndexPage';

function HorairesPage() {
    return (
        <IndexPage
            serviceName="Service Transport - Horaires"
            endpoint="horaires"
            fields={['date', 'heureDepart', 'heureArrivee']}
        />
    );
}

export default HorairesPage;
