import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { FeatureFilter } from '../../../shared/filters/feature-filter';
import { MatSelectModule } from "@angular/material/select";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { MatPaginator, MatPaginatorModule } from "@angular/material/paginator";
import { RouterLink } from '@angular/router';
import { Subject } from 'rxjs/internal/Subject';
import { Feature } from '../../../shared/interfaces/feature';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { FeatureService } from '../../../shared/services/feature-service/feature.service';
import { takeUntil } from 'rxjs';
import { Sort } from '../../../core/sort';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-feature-list',
    standalone: true,
    imports: [
        MatFormFieldModule, MatIconModule, MatSelectModule, MatTableModule, MatPaginatorModule,
        RouterLink, MatInputModule, CommonModule, FormsModule
    ],
    templateUrl: './feature-list.component.html',
    styleUrl: './feature-list.component.css'
})
export class FeatureListComponent implements OnInit, OnDestroy {
    private destroy = new Subject<void>();

    protected dataSource: MatTableDataSource<Feature> = new MatTableDataSource<Feature>([]);
    columnsToDisplay: string[] = ['name', 'actions'];

    @ViewChild(MatPaginator) paginator!: MatPaginator;
    filter: FeatureFilter;
    sort: Sort;

    constructor(private service: FeatureService) {
        this.filter = { name: '' };
        this.sort = {
            sortBy: 'name',
            ascending: true
        }
    }

    ngOnInit(): void {
        this.service.getAll(this.sort, this.filter).pipe(
            takeUntil(this.destroy)
        ).subscribe(response => {
            this.dataSource.data = response.body ?? [];
            this.dataSource.paginator = this.paginator;
        });
    }

    search(): void {
        this.service.getAll(this.sort, this.filter).pipe(
            takeUntil(this.destroy)
        ).subscribe(response => {
            this.dataSource.data = response.body ?? [];
        });
    }

    ngOnDestroy(): void {
        this.destroy.complete();
    }
}
