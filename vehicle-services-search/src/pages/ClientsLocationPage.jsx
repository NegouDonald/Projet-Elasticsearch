import IndexPage from './IndexPage';

function ClientsLocationPage() {
    return (
        <IndexPage
            serviceName="Service Location - Clients"
            endpoint="clients_location"
            fields={['nom', 'prenom', 'email', 'telephone']}
        />
    );
}

export default ClientsLocationPage;
