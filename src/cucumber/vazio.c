#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>



void main(void) {
	setuid(0);
	system("/bin/bash vazio.sh");
}
