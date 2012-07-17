#!/bin/sh

groovy CreateDeb.groovy
sh create-deb.sh
sh deploy.sh
