@echo off
cd /d "G:\Mi unidad\PC COMPARTIDOS\Proyectos\YagoDiaz-TFG\V_4 (API + Hibernate + FX + Jackson)\ORS_TALES"

echo ============================
echo   ACTUALIZANDO DESDE GITHUB
echo ============================

REM Esto descarta cualquier cambio local y deja la rama igual que el remoto
git fetch origin
git reset --hard origin/master

echo.
echo ==== Repositorio actualizado ====
pause
