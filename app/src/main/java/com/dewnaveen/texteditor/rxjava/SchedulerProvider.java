package com.dewnaveen.texteditor.rxjava;

import io.reactivex.Scheduler;


public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
