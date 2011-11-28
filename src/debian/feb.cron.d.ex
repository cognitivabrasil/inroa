#
# Regular cron jobs for the feb package
#
0 4	* * *	root	[ -x /usr/bin/feb_maintenance ] && /usr/bin/feb_maintenance
