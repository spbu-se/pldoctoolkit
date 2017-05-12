#!/usr/bin/env python3
# -*- coding: utf-8 -*-

def perform(clones, candidates, lgr, **kwargs):
    """
    Function to write extra reports
    :param clones: clones module VariativeElement
    :param candidates: list of clones.VariativeElement instances 
    :param lgr: logger to log =)
    :param kwargs: different parameters
    :return: nothing
    """
    lgr.info("==== Extra report ====")

    def vyugu_2017_1():
        """
        Запускаем старый алгоритм, а потом проверем кандидатов, котрые новый отбросил бы
        """
        clones.VariativeElement.postfiltering = True

        total_off = list(filter(lambda c: not (c.obeys_basset_constraint() and c.passes_filter()), candidates))
        lgr.info(" - lost NDG: %d" % len(list(filter(lambda ve: len(ve.clone_groups) > 1, total_off))))
        lgr.info(" - lost EDG: %d" % len(list(filter(lambda ve: len(ve.clone_groups) == 1, total_off))))
        cohtml = clones.VariativeElement.summaryhtml(total_off, clones.ReportMode.variative)
        with open("vyugu_2017_1_total_off.html", 'w', encoding='utf-8') as htmlfile:
            htmlfile.write(cohtml)

        non_bassett = list(filter(lambda c: not c.obeys_basset_constraint(), candidates))
        lgr.info(" - non-Bassett: %d" % len(non_bassett))
        cohtml = clones.VariativeElement.summaryhtml(non_bassett, clones.ReportMode.variative)
        with open("vyugu_2017_1_non_bassett.html", 'w', encoding='utf-8') as htmlfile:
            htmlfile.write(cohtml)

        ungrps = list(filter(lambda ve: len(ve.clone_groups) == 1, candidates))
        for nb in non_bassett:
            for g in nb.clone_groups:
                ungrps.append(clones.VariativeElement([g]))

        ungrps = list(filter(lambda c: not c.passes_filter(), ungrps))
        lgr.info(" - bad exact groups and parts of non-Bassett: %d" % len(ungrps))
        cohtml = clones.VariativeElement.summaryhtml(ungrps, clones.ReportMode.variative)
        with open("vyugu_2017_1_thrown_away.html", 'w', encoding='utf-8') as htmlfile:
            htmlfile.write(cohtml)

    # vyugu_2017_1()

    def vyugu_2017_3():
        """
        Запускаем новый алгоритм, а потом проверем кандидатов, котрые появились лишь благодяря склейке        
        """
        clones.ExactCloneGroup.prefiltering = True

        def fails_old_check(c):
            for g in c.clone_groups:
                if g.ntokens < 5 or not g.isCorrect():
                    return True
            return False

        c = list(filter(fails_old_check, candidates))

        lgr.info(" - exact dup groups: %d" % len(list(filter(lambda ve: len(ve.clone_groups) == 1, c))))
        lgr.info(" - near  dup groups: %d" % len(list(filter(lambda ve: len(ve.clone_groups) >  1, c))))

        cohtml = clones.VariativeElement.summaryhtml(c, clones.ReportMode.variative)
        with open("vyugu_2017_3.html", 'w', encoding='utf-8') as htmlfile:
            htmlfile.write(cohtml)

    # vyugu_2017_3()

    pass