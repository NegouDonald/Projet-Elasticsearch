import { useState, useEffect } from 'react';
   import axios from 'axios';

   function ConducteursPage() {
       const [conducteurs, setConducteurs] = useState([]);
       const [loading, setLoading] = useState(true);
       const [error, setError] = useState(null);

       useEffect(() => {
           axios.get('http://localhost:8080/api/conducteurs')
               .then(response => {
                   console.log('Conducteurs API Response:', response.data);
                   setConducteurs(response.data);
                   setLoading(false);
               })
               .catch(err => {
                   console.error('Conducteurs API Error:', err);
                   setError('Erreur lors du chargement des conducteurs');
                   setLoading(false);
               });
       }, []);

       if (loading) return <div className="text-gray-800">Chargement...</div>;
       if (error) return <div className="text-red-600">{error}</div>;

       return (
           <div className="p-4">
               <h1 className="text-2xl font-bold mb-4 text-gray-800">Conducteurs - Service Transport</h1>
               <ul className="space-y-4">
                   {conducteurs.length > 0 ? (
                       conducteurs.map(conducteur => (
                           <li key={conducteur.id} className="p-4 bg-white rounded-lg shadow-md">
                               <h3 className="text-lg font-semibold text-gray-900">{conducteur.nom} {conducteur.prenom}</h3>
                               <p className="text-gray-700">Adresse: {conducteur.adresse}</p>
                               <p className="text-gray-700">Numéro de permis: {conducteur.numeroPermis}</p>
                           </li>
                       ))
                   ) : (
                       <p className="text-gray-700">Aucun conducteur disponible</p>
                   )}
               </ul>
           </div>
       );
   }

   export default ConducteursPage;
