import IndexPage from './IndexPage';

function InstructeursPage() {
    return (
        <IndexPage
            serviceName="Service Auto-école - Instructeurs"
            endpoint="instructeurs"
            fields={['nom', 'prenom', 'adresse', 'dateNaissance', 'numeroLicence']}
        />
    );
}

export default InstructeursPage;
