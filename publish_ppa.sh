#!/bin/sh

groovy CreateDeb.groovy
sh clean.sh
sh create-deb.sh
sh deploy.sh
