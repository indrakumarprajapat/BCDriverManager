package in.boomcabs.drivermanager.firebase.database;

import com.google.cloud.firestore.Firestore;
import in.boomcabs.drivermanager.firebase.database.AbstractFirestoreRepository;
import in.boomcabs.drivermanager.firebase.database.DriverFirestore;
import org.springframework.stereotype.Repository;

@Repository
public class DriversRepository extends AbstractFirestoreRepository<DriverFirestore> {
    protected DriversRepository(Firestore firestore) {
        super(firestore, "drivers");
    }
}
