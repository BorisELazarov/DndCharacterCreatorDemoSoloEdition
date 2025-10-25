import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Feature } from '../../interfaces/feature';
import { Observable } from 'rxjs/internal/Observable';
import { FeatureFilter } from '../../filters/feature-filter';
import { Sort } from '../../../core/sort';

@Injectable({
  providedIn: 'root'
})
export class FeatureService {
  readonly url: string='http://localhost:8080/api/features';
  
    constructor(private httpClient:HttpClient) {}
    public getAllDeleted(sort:Sort,filter:FeatureFilter): Observable<HttpResponse<Feature[]>>{
      return this.httpClient
        .post<Feature[]>(
          this.url+'/getAll/deleted',{
            filter:filter,
            sort:sort
          },
          {observe:'response'}
        );
    }
    public getAllUnfiltered(): Observable<HttpResponse<Feature[]>>{
      return this.httpClient.get<Feature[]>(this.url,{observe:'response'});
    }
    public getAll(sort:Sort, filter:FeatureFilter): Observable<HttpResponse<Feature[]>>{
      return this.httpClient
        .post<Feature[]>(
          this.url+'/getAll',{
            filter:filter,
            sort:sort
          },
          {observe:'response'}
        );
    }
    public getById(id:number): Observable<HttpResponse<Feature>>{
      return this.httpClient
        .get<Feature>(this.url+'/'+id,{observe:'response'});
    }
    public create(feature: Feature):Observable<Feature>{
      return this.httpClient.post<Feature>(this.url, feature);
    }
    public edit(feature: Feature):Observable<Feature>{
      return this.httpClient.put<Feature>(this.url, feature);
    }
    public delete(id:number):Observable<Feature>{
      return this.httpClient.delete<Feature>(this.url+"?id="+id);
    }
    public confirmedDelete(id:number):Observable<Feature>{
      return this.httpClient.delete<Feature>(this.url+"/confirmedDelete?id="+id);
    }
    public restore(id:number):Observable<Feature>{
      return this.httpClient.put<Feature>(this.url+"/restore/"+id,null);
    }
}
