package com.techwave.elasticsearch.model;

public enum IndexMetadata {
    ELEVES("eleves", "Service Auto-école - Élèves", "http://localhost:5173/auto-ecole/eleves"),
    INSTRUCTEURS("instructeurs", "Service Auto-école - Instructeurs", "http://localhost:5173/auto-ecole/instructeurs"),
    LECONS("lecons", "Service Auto-école - Leçons", "http://localhost:5173/auto-ecole/lecons"),
    VEHICULES_AUTO_ECOLE("vehicules_auto_ecole", "Service Auto-école - Véhicules", "http://localhost:5173/auto-ecole/vehicules"),
    VOITURES_LOCATION("voitures_location", "Service Location de Voitures", "http://localhost:5173/location/voitures"),
    CLIENTS_LOCATION("clients_location", "Service Location - Clients", "http://localhost:5173/location/clients"),
    RESERVATIONS_LOCATION("reservations_location", "Service Location - Réservations", "http://localhost:5173/location/reservations"),
    ITINERAIRES("itineraires", "Service Transport - Itinéraires", "http://localhost:5173/transport/itineraires"),
    VEHICULES_TRANSPORT("vehicules_transport", "Service Transport - Véhicules", "http://localhost:5173/transport/vehicules"),
    CONDUCTEURS("conducteurs", "Service Transport - Conducteurs", "http://localhost:5173/transport/conducteurs"),
    HORAIRES("horaires", "Service Transport - Horaires", "http://localhost:5173/transport/horaires");

    private final String indexName;
    private final String serviceName;
    private final String webUrl;

    IndexMetadata(String indexName, String serviceName, String webUrl) {
        this.indexName = indexName;
        this.serviceName = serviceName;
        this.webUrl = webUrl;
    }

    public String getIndexName() { return indexName; }
    public String getServiceName() { return serviceName; }
    public String getWebUrl() { return webUrl; }
}