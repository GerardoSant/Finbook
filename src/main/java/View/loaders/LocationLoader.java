package View.loaders;

import Model.Location.Location;

public interface LocationLoader {

    Location load(int postalCode, String countryCode);

}
