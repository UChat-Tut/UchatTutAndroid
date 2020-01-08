package com.tla.uchattut.presentation.schedule.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.schedule.view_model.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.coroutines.*
import org.threeten.bp.YearMonth
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.WeekFields
import java.util.*
import javax.inject.Inject

class ScheduleFragment : Fragment() {

    @Inject
    lateinit var viewModel: ScheduleViewModel

    private lateinit var months: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.scheduleComponent(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        months = resources.getStringArray(R.array.months)

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.calendarDayTextView.text = day.date.dayOfMonth.toString()

                when {
                    day.date.isEqual(ZonedDateTime.now().chronology.dateNow()) -> {
                        container.calendarDayTextView.setTextColor(resources.getColor(android.R.color.white))
                        container.calendarDayView.background =
                            resources.getDrawable(R.drawable.background_day_calendar_current)
                    }
                    day.owner == DayOwner.THIS_MONTH -> {
                        container.calendarDayTextView.setTextColor(resources.getColor(android.R.color.black))
                        container.calendarDayView.background =
                            resources.getDrawable(R.drawable.background_day_calendar_in_month)
                    }
                    else -> {
                        container.calendarDayTextView.setTextColor(resources.getColor(R.color.silverChariot))
                        container.calendarDayView.background =
                            resources.getDrawable(R.drawable.background_day_calendar_out_month)
                    }
                }
            }
        }

        calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthHeaderViewContainer> {
                override fun create(view: View): MonthHeaderViewContainer =
                    MonthHeaderViewContainer(view)

                @SuppressLint("SetTextI18n")
                override fun bind(container: MonthHeaderViewContainer, month: CalendarMonth) {
                    container.calendarMonthText.text = "${months[month.month - 1]}, ${month.year}"

                    container.backButton.setOnClickListener {
                        calendarView.smoothScrollToMonth(month.yearMonth.minusMonths(1))
                    }

                    container.forwardButton.setOnClickListener {
                        calendarView.smoothScrollToMonth(month.yearMonth.plusMonths(1))
                    }
                }
            }


        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        calendarView.run {
            inDateStyle = InDateStyle.ALL_MONTHS
            outDateStyle = OutDateStyle.END_OF_GRID
            scrollMode = ScrollMode.PAGED
            orientation = RecyclerView.HORIZONTAL
            hasBoundaries = true
            maxRowCount = 6
            setup(firstMonth, lastMonth, firstDayOfWeek)
            scrollToMonth(currentMonth)
        }
    }
}