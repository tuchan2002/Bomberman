import com.almasb.fxgl.entity.component.Component;

public class GhostComponent extends Component {
    private int speed = 4;
    public GhostComponent() {

    }

    @Override
    public void onUpdate(double tpf) {
        if(entity.getX() < 200 && speed < 0) {
            speed = 4;
        } else if(entity.getX() > 400 && speed > 0) {
            speed = -4;
        }

        entity.translateX(speed);


    }
}
