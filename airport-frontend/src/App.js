import React, { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [airports, setAirports] = useState([]);
  const [source, setSource] = useState("");
  const [destination, setDestination] = useState("");
  const [itinerary, setItinerary] = useState([]);
  const [loading, setLoading] = useState(false);

  // Fetch airports on mount
  useEffect(() => {
    fetch("http://localhost:8080/api/airports")
      .then((res) => res.json())
      .then((data) => setAirports(data));
  }, []);

  const handleFindPath = () => {
    if (!source || !destination) return;
    setLoading(true);
    fetch(
      `http://localhost:8080/api/shortest-path?source=${source}&destination=${destination}`
    )
      .then((res) => res.json())
      .then((data) => {
        setItinerary(data);
        setLoading(false);
      });
  };

  return (
    <div className="homepage-container">
      <div className="homepage-card">
        <h1 className="homepage-title">SkyConnect</h1>
        <div className="homepage-field">
          <label>
            Source Airport:{" "}
            <select value={source} onChange={(e) => setSource(e.target.value)} className="homepage-select">
              <option value="">Select</option>
              {airports.map((a) => (
                <option key={a.code} value={a.code}>
                  {a.code} - {a.name}
                </option>
              ))}
            </select>
          </label>
        </div>
        <div className="homepage-field">
          <label>
            Destination Airport:{" "}
            <select value={destination} onChange={(e) => setDestination(e.target.value)} className="homepage-select">
              <option value="">Select</option>
              {airports.map((a) => (
                <option key={a.code} value={a.code}>
                  {a.code} - {a.name}
                </option>
              ))}
            </select>
          </label>
        </div>
        <button
          className="homepage-button"
          onClick={handleFindPath}
          disabled={loading || !source || !destination}
        >
          {loading ? "Finding..." : "Find Shortest Path"}
        </button>
        <div className="homepage-itinerary">
          {itinerary.length > 0 && (
            <div>
              <h2>Itinerary</h2>
              <ul>
                {itinerary.map((step, idx) => (
                  <li key={idx}>{step}</li>
                ))}
              </ul>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default App;