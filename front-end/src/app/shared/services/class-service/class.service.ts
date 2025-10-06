import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sort } from '../../../core/sort';
import { DndClass } from '../../../shared/interfaces/dnd-class';
import { Filter } from '../../../features/classes/filter';
import { ClassListItem } from './class-list-item';

@Injectable({
  providedIn: 'root'
})
export class ClassService {
  readonly url: string='http://localhost:8080/api/classes';

  constructor(private httpClient:HttpClient) { }

  getAllDeleted(sort: Sort, filter: Filter):Observable<HttpResponse<ClassListItem[]>> {
    return this.httpClient
      .post<ClassListItem[]>(
        this.url+'/getAll/deleted',{
          filter:filter,
          sort:sort
        },
        {observe:'response'}
      );
  }

  getAll(sort: Sort, filter: Filter):Observable<HttpResponse<ClassListItem[]>> {
    return this.httpClient
      .post<ClassListItem[]>(
        this.url+'/getAll',{
          filter:filter,
          sort:sort
        },
        {observe:'response'}
      );
  }

  getAllUnfiltered():Observable<HttpResponse<DndClass[]>> {
    return this.httpClient
      .get<DndClass[]>(
        this.url,
        {observe:'response'}
      );
  }

  public getById(id:number): Observable<HttpResponse<DndClass>>{
    return this.httpClient
      .get<DndClass>(this.url+'/'+id,{observe:'response'});
  }

  public create(dndClass: DndClass):Observable<DndClass>{
    return this.httpClient.post<DndClass>(this.url, dndClass);
  }

  public edit(dndClass: DndClass):Observable<DndClass>{
    return this.httpClient.put<DndClass>(this.url, dndClass);
  }

  public delete(id:number):Observable<DndClass>{
    return this.httpClient.delete<DndClass>(this.url+"?id="+id);
  }

  public confirmedDelete(id:number):Observable<DndClass>{
    return this.httpClient.delete<DndClass>(this.url+"/confirmedDelete?id="+id);
  }

  public restore(id:number):Observable<DndClass>{
    return this.httpClient.put<DndClass>(this.url+"/restore/"+id,null);
  }
}
