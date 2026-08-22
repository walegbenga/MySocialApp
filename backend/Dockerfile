FROM composer:2 AS composer
FROM php:8.4-cli-alpine
RUN apk add --no-cache postgresql-dev $PHPIZE_DEPS && docker-php-ext-install pdo_pgsql bcmath opcache && pecl install redis && docker-php-ext-enable redis && apk del $PHPIZE_DEPS
COPY --from=composer /usr/bin/composer /usr/bin/composer
WORKDIR /var/www/html
EXPOSE 8000
CMD ["sh", "-lc", "if [ ! -f vendor/autoload.php ]; then composer install --no-interaction; fi; php artisan serve --host=0.0.0.0 --port=8000"]
