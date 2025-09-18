@echo off
REM Ir al directorio del repo
cd /d "G:\Mi unidad\PC COMPARTIDOS\Proyectos\YagoDiaz-TFG\V_4 (API + Hibernate + FX + Jackson)\ORS_TALES"

REM Añadir todos los cambios
git add .

REM Hacer commit con fecha y hora en el mensaje
git commit -m "Auto-commit %date% %time%"

REM Subir cambios al remoto
git push origin master

pause
