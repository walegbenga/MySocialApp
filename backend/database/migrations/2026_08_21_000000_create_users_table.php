<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    public function up(): void { Schema::create('users', function (Blueprint $table): void { $table->id(); $table->string('username', 30)->unique(); $table->string('name', 80); $table->string('email')->unique(); $table->string('password'); $table->text('bio')->nullable(); $table->timestamps(); }); }
    public function down(): void { Schema::dropIfExists('users'); }
};
