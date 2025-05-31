export default function DocumentList({ documents }) {
    return (
      <div className="p-4">
        {documents.map((doc) => (
          <div key={doc.id} className="border-b py-2">
            <h2 className="text-xl font-bold">{doc.title}</h2>
            <p>{doc.content}</p>
          </div>
        ))}
      </div>
    );
  }
  