import axios from "axios";

const API_URL = "http://localhost:8080/api/documents";

export const saveDocument = (doc) => axios.post(API_URL, doc);

export const searchDocuments = (q) =>
  axios.get(`${API_URL}/search?q=${encodeURIComponent(q)}`);

export const getAllDocuments = () => axios.get(API_URL);
