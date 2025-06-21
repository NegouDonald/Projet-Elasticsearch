import IndexPage from './IndexPage';

function ReservationsLocationPage() {
    return (
        <IndexPage
            serviceName="Service Location - Réservations"
            endpoint="reservations_location"
            fields={['dateDebut', 'dateFin', 'statut']}
        />
    );
}

export default ReservationsLocationPage;
