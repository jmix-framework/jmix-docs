package charts.ex1.screen.charts.config;

import charts.ex1.entity.DateValueVolume;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@UiController("sample_MultipleValueaxisChart")
@UiDescriptor("multiple-valueaxis-chart.xml")
public class MultipleValueaxisChart extends Screen {
    private static final int DAYS_COUNT = 20;

    @Autowired
    private CollectionContainer<DateValueVolume> lineChartDc;
    @Autowired
    private Metadata metadata;
    @Autowired
    private TimeSource timeSource;

    private Random random = new Random();

    @Subscribe
    protected void onInit(InitEvent event) {
        List<DateValueVolume> items = new ArrayList<>();
        Date startDate = DateUtils.addDays(timeSource.currentTimestamp(), -DAYS_COUNT);
        for (int i = 0; i < DAYS_COUNT; i++) {
            items.add(generateDateValueVolume(DateUtils.addDays(startDate, i), i));
        }
        lineChartDc.setItems(items);
    }


    private DateValueVolume generateDateValueVolume(Date date, int i) {
        Long value = Math.round(random.nextDouble() * (20 + i)) + 20 + i;
        Long volume = Math.round(random.nextDouble() * (20 + i)) + i;
        return dateValueVolume(date, value, volume);
    }

    private DateValueVolume dateValueVolume(Date date, Long value, Long volume) {
        DateValueVolume dateValueVolume = metadata.create(DateValueVolume.class);
        dateValueVolume.setDate(date);
        dateValueVolume.setValue(value);
        dateValueVolume.setVolume(volume);
        return dateValueVolume;
    }
}