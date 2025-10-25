import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatPaginator, MatPaginatorModule } from "@angular/material/paginator";
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Subject, takeUntil } from 'rxjs';
import { FeatureFilter } from '../../../shared/filters/feature-filter';
import { Feature } from '../../../shared/interfaces/feature';
import { FeatureService } from '../../../shared/services/feature-service/feature.service';
import { Sort } from '../../../core/sort';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-feature-list-deleted',
  standalone: true,
  imports: [
    MatFormFieldModule, MatIconModule, MatSelectModule, MatTableModule, MatPaginatorModule,
    RouterLink, MatInputModule, CommonModule, FormsModule
  ],
  templateUrl: './feature-list-deleted.component.html',
  styleUrl: './feature-list-deleted.component.css'
})
export class FeatureListDeletedComponent implements OnInit, OnDestroy {
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
    this.service.getAllDeleted(this.sort, this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response => {
      this.dataSource.data = response.body ?? [];
    });
  }

  openDialog(id: number) {
    if (confirm("Are you sure to delete this once and for all?")) {
      this.delete(id);
    }
  }

  restore(id: number): void {
    this.service.restore(id).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.removeFromDataSource(id);
  }

  private delete(id: number): void {
    this.service.confirmedDelete(id).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.removeFromDataSource(id);
  }

  removeFromDataSource(id: number): void {
    this.dataSource.data = this.dataSource.data.filter(x => x.id != id);
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}
