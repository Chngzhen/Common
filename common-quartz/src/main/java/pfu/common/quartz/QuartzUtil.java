package pfu.common.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class QuartzUtil {

    private static final Logger log = LoggerFactory.getLogger(QuartzUtil.class);

    private QuartzUtil() {}


    public static boolean schedule(Scheduler scheduler, String taskId, String jobClass, String cronExp) {
        Class<? extends Job> clazz;
        try {
            clazz = Class.forName(jobClass).asSubclass(Job.class);
        } catch (Exception e) {
            log.error("作业类{}加载失败", jobClass, e);
            return false;
        }
        return schedule(scheduler, taskId, clazz, cronExp);
    }

    /**
     * 基于Cron表达式调度指定作业。
     *
     * @param scheduler 调度器。
     * @param taskId 任务标识，用于构建作业和触发器的KEY。
     * @param jobClass 作业类。
     * @param cronExp Cron表达式。
     * @return true - 调度成功
     */
    public static boolean schedule(Scheduler scheduler, String taskId, Class<? extends Job> jobClass, String cronExp) {
        try {
            cleanup(scheduler, taskId);

            String triggerName = buildTriggerName(taskId);
            String jobName = buildJobName(taskId);

            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName)
                    .usingJobData("taskId", taskId)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (Exception e) {
            log.error("调度失败", e);
            return false;
        }
    }


    public static boolean schedule(Scheduler scheduler, String taskId, String jobClass, int period, TimeUnit timeUnit) {
        Class<? extends Job> clazz;
        try {
            clazz = Class.forName(jobClass).asSubclass(Job.class);
        } catch (Exception e) {
            log.error("作业类{}加载失败", jobClass, e);
            return false;
        }
        return schedule(scheduler, taskId, clazz, period, timeUnit);
    }

    /**
     * 间隔调度指定作业。
     *
     * @param scheduler 调度器。
     * @param taskId 任务标识，用于构建作业和触发器的KEY。
     * @param jobClass 作业类。
     * @param period 执行间隔。
     * @param timeUnit 执行间隔的单位。
     * @return true - 调度成功
     */
    public static boolean schedule(Scheduler scheduler, String taskId, Class<? extends Job> jobClass, int period, TimeUnit timeUnit) {
        try {
            cleanup(scheduler, taskId);

            String triggerName = buildTriggerName(taskId);
            String jobName = buildJobName(taskId);

            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName)
                    .usingJobData("taskId", taskId)
                    .build();

            ScheduleBuilder<? extends Trigger> scheduleBuilder;
            switch (timeUnit) {
                case SECONDS:
                    scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(period);
                    break;
                case MINUTES:
                    scheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(period);
                    break;
                case HOURS:
                    scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForever(period);
                    break;
                default:
                    log.error("不支持的时间单位");
                    return false;
            }

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName)
                    .withSchedule(scheduleBuilder)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (Exception e) {
            log.error("调度失败", e);
            return false;
        }
    }

    /**
     * 暂停任务。注意：若任务不存在，为避免恢复报错，视作暂停失败。
     *
     * @param scheduler 调度器
     * @param taskId 任务标识
     * @return true - 暂停成功
     */
    public static boolean pause(Scheduler scheduler, String taskId) {
        try {
            String jobName = buildJobName(taskId);
            JobKey jobKey = new JobKey(jobName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (null == jobDetail) {
                return false;
            }
            // pauseJob 内部会调用 pauseTrigger。
            scheduler.pauseJob(jobKey);
            return true;
        } catch (Exception e) {
            log.error("任务暂停失败", e);
            return false;
        }
    }

    public static boolean pauseAll(Scheduler scheduler) {
        try {
            scheduler.pauseAll();
            return true;
        } catch (Exception e) {
            log.error("调度器暂停失败", e);
            return false;
        }
    }

    /**
     * 恢复任务。注意：若任务不存在，视作恢复失败。
     *
     * @param scheduler 调度器
     * @param taskId 任务标识
     * @return true - 恢复成功
     */
    public static boolean resume(Scheduler scheduler, String taskId) {
        try {
            String jobName = buildJobName(taskId);
            JobKey jobKey = new JobKey(jobName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (null == jobDetail) {
                return false;
            }
            // resumeJob 内部会调用 resumeTrigger。
            scheduler.resumeJob(jobKey);
            return true;
        } catch (Exception e) {
            log.error("任务恢复失败", e);
            return false;
        }
    }

    public static boolean resumeAll(Scheduler scheduler) {
        try {
            scheduler.resumeAll();
            return true;
        } catch (Exception e) {
            log.error("调度器恢复失败", e);
            return false;
        }
    }

    public static void cleanup(Scheduler scheduler, String taskId) throws SchedulerException {
        clearTrigger(scheduler, taskId);
        clearJob(scheduler, taskId);
    }

    public static void clearTrigger(Scheduler scheduler, String taskId) throws SchedulerException {
        String triggerName = buildTriggerName(taskId);
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (null != trigger) {
            log.info("触发器{}存在！执行删除。", triggerName);
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
        }
    }

    public static void clearJob(Scheduler scheduler, String taskId) throws SchedulerException {
        String jobName = buildJobName(taskId);
        JobKey jobKey = JobKey.jobKey(jobName);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (null != jobDetail) {
            log.info("作业{}存在！执行删除。", jobName);
            scheduler.deleteJob(jobKey);
        }
    }

    public static String buildTriggerName(String taskId) {
        return "T_" + taskId;
    }

    public static String buildJobName(String taskId) {
        return "J_" + taskId;
    }

}
