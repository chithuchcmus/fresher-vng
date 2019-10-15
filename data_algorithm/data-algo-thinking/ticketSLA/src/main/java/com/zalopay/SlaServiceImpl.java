package com.zalopay;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by thinhda.
 * Date: 2019-04-15
 */

public class SlaServiceImpl implements SlaService {
    final private LocalTime beginMorning = LocalTime.of(8,30,0);
    final private  LocalTime endMorning = LocalTime.of(12,0,0);
    final private  LocalTime beginEvening= LocalTime.of(13,30,0);
    final private  LocalTime endEvening= LocalTime.of(18,0,0);
    final  private  int SATURDAY = 6;
    final  private  int SUNDAY = 7;

    public SlaServiceImpl()
    {

    }

    @Override
    public Duration calculate(LocalDateTime begin, LocalDateTime end) {
        // TODO: calc working hours

        begin = convertTimeBegin(begin);
        LocalDate daybegin = begin.toLocalDate();
        LocalDate dayend = end.toLocalDate();
        LocalDate nextDayofBegin = daybegin.plusDays(1);
        LocalDate previousDayOfEnd = dayend.plusDays(-1);
        Duration timeworkResult;
        //nếu cùng 1 ngày
        if(daybegin.isEqual(dayend))
        {
            timeworkResult=  calculateTimeSameDay(begin,end);
        }
        else
        {
            // nếu là ngày kế tiếp
            if(dayend.isEqual(nextDayofBegin))
            {
                timeworkResult = caculateTimeComplainToEndDay(begin);
                timeworkResult = timeworkResult.plus(caculateTimeResolveCompaintFromBeginDay(end));
            }
            else
            {
                // nếu cách nhau hơn 1 ngày
                //tính từ khi bắt đầu đến cuối ngày hôm đó
                timeworkResult = caculateTimeComplainToEndDay(begin);
                //tính từ  lúc đầu ngày đến hết ngày ở ngày cuối
                timeworkResult = timeworkResult.plus(caculateTimeResolveCompaintFromBeginDay(end));
                //tính từ ngày begin + 1 -> ngày cuối -1
                while (!nextDayofBegin.isAfter(previousDayOfEnd))
                {
                    timeworkResult = timeworkResult.plus(caculaTimeWordPerDay(nextDayofBegin));
                    //cộng 1 ngày vào
                    nextDayofBegin = nextDayofBegin.plusDays(1);
                }
            }
        }
        return timeworkResult;
    }

    public Duration calculateTimeSameDay(LocalDateTime begin, LocalDateTime end)
    {
        Duration timework;
        if(isInMorning(begin))
        {
            if(isInMorning(end))
            {
                timework = Duration.between(begin,end);
            }
            else
            {
                timework = caculateTimeWorkToEndMorning(begin.toLocalTime());
                timework = timework.plus(caculateTimeWorkFromBeginEveing(end.toLocalTime()));
            }
        }
        else
        {
            timework = Duration.between(begin,end);
        }
        return timework;
    }

    public boolean isInMorning(LocalDateTime time)
    {
        LocalTime timebegin = time.toLocalTime();
        if(timebegin.compareTo(beginMorning) >= 0 && timebegin.compareTo(endMorning) <=0)
        {
            return true;
        }
        return false;
    }

    public boolean isInEvening(LocalDateTime time)
    {
        LocalTime timebegin = time.toLocalTime();
        if(timebegin.compareTo(beginEvening) > 0 && timebegin.compareTo(endEvening) <0)
        {
            return true;
        }
        return false;
    }


    public LocalDateTime convertTimeBegin(LocalDateTime begin)
    {
        LocalTime timebegin= begin.toLocalTime();
        LocalDate daybegin = begin.toLocalDate();
        int dayPerWeek = begin.getDayOfWeek().getValue();

        if(dayPerWeek==SUNDAY)
        {
            timebegin=LocalTime.of(8,30);
            daybegin = daybegin.plusDays(1);
        }
        else
        {
            if(timebegin.compareTo(beginMorning) <0 )
            {
                timebegin=LocalTime.of(8,30);
                return LocalDateTime.of(daybegin,timebegin);
            }
            else if(timebegin.compareTo(endEvening) > 0)
            {

                timebegin=LocalTime.of(8,30);
                if(dayPerWeek == SATURDAY)
                {
                    daybegin = daybegin.plusDays(2);
                }
                else
                {
                    daybegin = daybegin.plusDays(1);
                }
                return LocalDateTime.of(daybegin,timebegin);
            }
            else if(timebegin.compareTo(endMorning) > 0 && timebegin.compareTo(beginEvening) < 0)
            {
                if(dayPerWeek == SATURDAY)
                {
                    timebegin=LocalTime.of(8,30);
                    daybegin = daybegin.plusDays(2);
                }
                else
                {
                    timebegin=LocalTime.of(13,30);
                }
                return LocalDateTime.of(daybegin,timebegin);
            }
        }
        return begin;
    }


    public Duration caculateTimeComplainToEndDay(LocalDateTime begin)
    {
        Duration timeWork;
        int dayPerWeek = begin.getDayOfWeek().getValue();

        if(dayPerWeek == SUNDAY)
        {
            timeWork =  Duration.ZERO;
        }
        else
        {
            if(isInMorning(begin))
            {

                if(dayPerWeek == SATURDAY)
                {
                    timeWork = caculateTimeWorkToEndMorning(begin.toLocalTime());
                }
                else
                {
                    timeWork = caculateTimeWorkToEndMorning(begin.toLocalTime());
                    timeWork = timeWork.plus(caculateTimeWorkInEvening());
                }
            }
            else
            {
                if(dayPerWeek==SATURDAY)
                {
                    timeWork = Duration.ZERO;
                }
                else
                    timeWork = caculateTimeWorkToEndEvening(begin.toLocalTime());
            }
        }

        return  timeWork;
    }

    public  Duration caculateTimeResolveCompaintFromBeginDay(LocalDateTime end)
    {
        Duration timeWork;
        if(isInMorning(end))
        {
            timeWork = caculateTimeWorkFromBeginMorning(end.toLocalTime());
        }
        else
        {
            timeWork = caculateTimeWorkInMorning();
            timeWork = timeWork.plus(caculateTimeWorkFromBeginEveing(end.toLocalTime()));
        }
        return timeWork;
    }

    //tinh thoi gian lam viec buoi sang
    private  Duration caculateTimeWorkInMorning()
    {
        return Duration.between(beginMorning,endMorning);
    }

    //tinh thoi gian lam viec buoi chieu
    private  Duration caculateTimeWorkInEvening()
    {
        return Duration.between(beginEvening,endEvening);
    }

    //tinh thoi gian lam viec tu bat dau sang den thoi diem hien tai trong buoi sang
    private  Duration caculateTimeWorkFromBeginMorning( LocalTime timeInMorning)
    {
        return Duration.between(beginMorning,timeInMorning);
    }

    //tinh thoi gian lam viec tu bat dau  tai trong buoi sang den het sang
    private  Duration caculateTimeWorkToEndMorning( LocalTime timeInMorning)
    {
        return Duration.between(timeInMorning,endMorning);
    }

    //tinh thoi gian lam viec tu bat dau chieu den thoi diem hien tai trong buoi chieu
    private  Duration caculateTimeWorkFromBeginEveing( LocalTime timeInEveing)
    {
        return Duration.between(beginEvening,timeInEveing);
    }

    //tinh thoi gian lam viec tu diem hien tai trong buoi chieu den het chieu
    private Duration caculateTimeWorkToEndEvening(LocalTime timeInEveing)
    {
        return Duration.between(timeInEveing,endEvening);
    }
    private  Duration caculaTimeWordPerDay(LocalDate timeWork)
    {
        int dayPerWeek = timeWork.getDayOfWeek().getValue();
        Duration timeWorkPerDay;
        switch (dayPerWeek){
            case SATURDAY:
            {
                timeWorkPerDay = caculateTimeWorkInMorning();
                break;
            }
            case SUNDAY:
            {
                timeWorkPerDay = Duration.ZERO;
                break;
            }
            default:
                timeWorkPerDay = caculateTimeWorkInMorning().plus(caculateTimeWorkInEvening());
                break;
        }
        return timeWorkPerDay;
    }



}
