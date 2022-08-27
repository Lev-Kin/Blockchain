
class ComputerFacadeTestDrive {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Monitor monitor = new Monitor();
        Keyboard keyboard = new Keyboard();

        ComputerFacade computerFacade = new ComputerFacade(processor, monitor, keyboard);

        computerFacade.turnOnComputer();
        computerFacade.turnOffComputer();
    }
}

class ComputerFacade {
    Processor processor;
    Monitor monitor;
    Keyboard keyboard;

    public ComputerFacade(Processor processor, Monitor monitor, Keyboard keyboard) {
        this.processor = processor;
        this.monitor = monitor;
        this.keyboard = keyboard;
    }

    public void turnOnComputer() {
        processor.on();
        monitor.on();
        keyboard.on();
    }

    public void turnOffComputer() {
        keyboard.off();
        monitor.off();
        processor.off();
    }
}

abstract class Subsystem {
    protected boolean isOn = false;

    abstract String getType();

    public void on() {
        isOn = true;
        System.out.println(getType() + " on");
    }

    public void off() {
        isOn = false;
        System.out.println(getType() + " off");
    }
}

class Processor extends Subsystem {
    @Override
    public String getType() {
        return "Processor";
    }
}

class Monitor extends Subsystem {
    @Override
    public String getType() {
        return "Monitor";
    }
}

class Keyboard extends Subsystem {

    @Override
    public String getType() {
        return "Keyboard";
    }

    private void turnOnBacklight() {
        System.out.println("Backlight is turned on");
    }

    private void turnOffBacklight() {
        System.out.println("Backlight is turned off");
    }

    @Override
    public void on() {
        super.on();
        turnOnBacklight();
    }

    @Override
    public void off() {
        super.off();
        turnOffBacklight();
    }
}

