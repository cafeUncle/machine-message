package com.bjfl.galaxymessage.jobs;

import com.bjfl.galaxymessage.mqtt.MqttSender;
import com.bjfl.galaxymessage.netty.NettyMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ScheduledTask {

    @Autowired
    MqttSender mqttSender;

    /**
     * 30秒上传一次心跳信息
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void heartBeat() {

        Set<String> keys = NettyMessageHandler.clientList.keySet();

        for (String key : keys) {
            mqttSender.sendHeartBeatsMessage(key);
        }

    }

    /**
     * 30秒检查一次超时任务
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void timeout(){

        Collection<CopyOnWriteArrayList<Job>> allJobs = JobBus.jobs.values();

        for (CopyOnWriteArrayList<Job> machineJobs : allJobs) {

            for (Job machineJob : machineJobs) {
                if (machineJob.isExpired()) {
                    /**
                     * 删除前，向后台发送超时提示
                     */
                    machineJobs.remove(machineJob);
                }
            }
        }
    }
}
