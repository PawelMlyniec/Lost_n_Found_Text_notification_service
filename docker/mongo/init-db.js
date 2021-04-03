db = db.getSiblingDB("tms-db")
db.createUser(
    {
        user: "tms",
        pwd: "tms",
        roles: [
            {
                role: "readWrite",
                db: "tms-db"
            }
        ]
    }
);

db = db.getSiblingDB("ns-db")
db.createUser(
    {
        user: "ns",
        pwd: "ns",
        roles: [
            {
                role: "readWrite",
                db: "ns-db"
            }
        ]
    }
);