package de.maanex.sysad;


import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CpuTerminal implements CommandExecutor {

	@SuppressWarnings("restriction")
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		String mes = "";

		mes += "\n§7======= §6System Space §7=======";

		OperatingSystemMXBean o = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		mes += "\n§7Available Processors§8:§6 " + o.getAvailableProcessors();

		long free = Runtime.getRuntime().freeMemory(), max = Runtime.getRuntime().maxMemory(), total = Runtime.getRuntime().totalMemory();
		int mb = 1024 * 1024;
		long used = total - free;
		mes += "\n§7Memory Total§8:§6 " + total / mb + "MB";
		mes += "\n§7Memory Max§8:§6 " + max / mb + "MB";
		mes += "\n§7Memory Used§8:§6 " + used / mb + "MB §7(" + (int) (((double) used / (double) total) * 100) + "%)";
		mes += "\n§7Memory Free§8:§6 " + free / mb + "MB §7(" + (int) (((double) free / (double) total) * 100) + "%)";

		mes += "\n\n§7======= §dSystem Workload §7=======";
		ThreadMXBean tmb = ManagementFactory.getThreadMXBean();

		mes += "\n§7Current Thread Cpu Time§8:§d " + (double) (tmb.getCurrentThreadCpuTime() / 10000 / 60) / 60 + "s";
		mes += "\n§7Thread Amount§8:§d " + tmb.getThreadCount();
		com.sun.management.OperatingSystemMXBean nosb = ManagementFactory.getPlatformMXBean(com.sun.management.OperatingSystemMXBean.class);
		mes += "\n§7Sys CPU load§8:§d " + (double) ((int) (nosb.getSystemCpuLoad() * 10000)) / 100 + "%";
		mes += "\n§7Pcs CPU load§8:§d " + (double) ((int) (nosb.getProcessCpuLoad() * 10000)) / 100 + "%";

		s.sendMessage(mes);
		return true;
	}

}
