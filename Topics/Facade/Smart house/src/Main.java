
class SmartHouseFacadeTestDrive {
    public static void main(String[] args) {
        StereoSystem stereoSystem = new StereoSystem();
        Bathroom bathroom = new Bathroom();
        Lights lights = new Lights();

        SmartHouseFacade smartHouseFacade = new SmartHouseFacade(stereoSystem, bathroom, lights);

        lights.setFavoriteColorTemperature("Calming blue");
        stereoSystem.setFavoriteSong("Queen - Killer Queen");
        bathroom.setFavoriteTemperature("35℃");
        bathroom.setFavoriteLevel("60%");

        smartHouseFacade.cameHome();
        smartHouseFacade.leaveBathroomGoSleep();
    }
}

class SmartHouseFacade {
    StereoSystem stereoSystem;
    Bathroom bathroom;
    Lights lights;

    public SmartHouseFacade(StereoSystem stereoSystem, Bathroom bathroom, Lights lights) {
        this.stereoSystem = stereoSystem;
        this.bathroom = bathroom;
        this.lights = lights;
    }

    public void cameHome() {
        stereoSystem.on();
        bathroom.fill();
        lights.on();
    }

    public void leaveBathroomGoSleep() {
        bathroom.drain();
        stereoSystem.off();
        lights.off();
    }
}

class StereoSystem {
    private String description = "StereoSystem";
    private String favoriteSong;

    public void on() {
        System.out.printf("%s on\n", description);
        turnOnFavoriteSong();
    }

    public void off() {
        System.out.printf("%s off\n", description);
    }

    private void turnOnFavoriteSong() {
        System.out.printf("Favorite song is playing! %s\n", favoriteSong);
    }

    public void setFavoriteSong(String favoriteSong) {
        this.favoriteSong = favoriteSong;
    }
}

class Bathroom {
    private String description = "The tub";
    private String favoriteTemperature;
    private String favoriteLevel;

    public void fill() {
        System.out.printf("%s is being filled\n", description);
        System.out.printf("Temperature: %s\n", favoriteTemperature);
        System.out.printf("Water level: %s\n", favoriteLevel);
    }

    public void drain() {
        System.out.printf("%s is being drained\n", description);
    }

    public void setFavoriteTemperature(String favoriteTemperature) {
        this.favoriteTemperature = favoriteTemperature;
    }

    public void setFavoriteLevel(String favoriteLevel) {
        this.favoriteLevel = favoriteLevel;
    }
}

class Lights {
    private String description = "Lights";
    private String favoriteColorTemperature;

    public void on() {
        System.out.printf("%s on\n", description);
        System.out.printf("Color temperature is: %s\n", favoriteColorTemperature);
    }

    public void off() {
        System.out.printf("%s off\n", description);
    }

    public void setFavoriteColorTemperature(String favoriteColorTemperature) {
        this.favoriteColorTemperature = favoriteColorTemperature;
    }
}


