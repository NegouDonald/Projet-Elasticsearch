import { useState, useEffect } from 'react';
   import { useParams } from 'react-router-dom';
   import axios from 'axios';

   function IndexPage({ serviceName, endpoint, fields }) {
       const { index } = useParams();
       const [data, setData] = useState([]);
       const [loading, setLoading] = useState(true);
       const [error, setError] = useState(null);

       useEffect(() => {
           axios.get(`http://localhost:8080/api/${endpoint}`)
               .then(response => {
                   console.log('API Response:', response.data); // Log pour débogage
                   setData(response.data);
                   setLoading(false);
               })
               .catch(err => {
                   console.error('API Error:', err);
                   setError(`Erreur lors du chargement des ${index}`);
                   setLoading(false);
               });
       }, [endpoint, index]);

       if (loading) return <div className="text-gray-800">Chargement...</div>;
       if (error) return <div className="text-red-600">{error}</div>;

       return (
           <div className="p-4">
               <h1 className="text-2xl font-bold mb-4 text-gray-800">{serviceName}</h1>
               <ul className="space-y-4">
                   {data.length > 0 ? (
                       data.map(item => (
                           <li key={item.id} className="p-4 bg-white rounded-lg shadow-md">
                               {fields.map(field => (
                                   <p key={field} className="text-gray-700">{field}: {item[field]}</p>
                               ))}
                           </li>
                       ))
                   ) : (
                       <p className="text-gray-700">Aucune donnée disponible</p>
                   )}
               </ul>
           </div>
       );
   }

   export default IndexPage;
