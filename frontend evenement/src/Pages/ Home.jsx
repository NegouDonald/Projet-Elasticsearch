import { useEffect, useState } from "react";
import { getAllDocuments, searchDocuments } from "../services/api";
import SearchBar from "../components/SearchBar";
import DocumentList from "../components/DocumentList";

export default function Home() {
  const [docs, setDocs] = useState([]);

  const fetchAll = async () => {
    const res = await getAllDocuments();
    setDocs(res.data);
  };

  const handleSearch = async (q) => {
    const res = await searchDocuments(q);
    setDocs(res.data);
  };

  useEffect(() => {
    fetchAll();
  }, []);

  return (
    <>
      <SearchBar onSearch={handleSearch} />
      <DocumentList documents={docs} />
    </>
  );
}
